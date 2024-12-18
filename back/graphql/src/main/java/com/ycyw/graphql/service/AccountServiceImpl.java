package com.ycyw.graphql.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ycyw.graphql.entity.AccountEntity;
import com.ycyw.graphql.generated.types.NewCustomerAccountInput;
import com.ycyw.graphql.generated.types.Account;
import com.ycyw.graphql.mapper.AccountEntityMapper;
import com.ycyw.graphql.repository.AccountRepository;
import com.ycyw.graphql.repository.AddressRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Account's service implementation
 */
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountEntityMapper accountMapper;
    private final AddressRepository addressRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, AccountEntityMapper accountMapper, AddressRepository addressRepository) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.addressRepository = addressRepository;
    }

    @Override
    public Mono<Account> getAccount(String id) {
        return accountRepository.findById(Long.parseLong(id))
        .flatMap(this::getAccountAddress)
        .map(accountMapper::entityToAccount);
    }

    @Override
    public Mono<Account> createAccount(NewCustomerAccountInput account) {
        AccountEntity accountToSave = accountMapper.newAccountToEntity(account);
        return this.addressRepository.save(accountToSave.getAddress()).flatMap(address -> {
            accountToSave.setAddressId(address.getId());
            accountToSave.setAddress(address);
            return this.accountRepository.save(accountToSave)
            .doOnError(error -> this.addressRepository.delete(address).then(Mono.error(error)))
            .map(accountMapper::entityToAccount);
        });
    }

    @Override
    public Flux<Account> getAccounts() {
        return accountRepository.findAll()
            .flatMap(this::getAccountAddress)
            .map(accountMapper::entityToAccount);
    }

    private Mono<AccountEntity> getAccountAddress(AccountEntity account) {
        return addressRepository.findById(account.getAddressId())
            .map(address -> {
                account.setAddress(address);
                return account;
            });
    }

}
