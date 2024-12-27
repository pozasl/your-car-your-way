package com.ycyw.graphql.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder encoder;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, AccountEntityMapper accountMapper, AddressRepository addressRepository, PasswordEncoder encoder) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.addressRepository = addressRepository;
        this.encoder = encoder;
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
            accountToSave.setPassword(encoder.encode(account.getPassword()));
            return this.accountRepository.save(accountToSave)
            .flatMap(acc -> this.accountRepository.findById(acc.getId()))
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

    @Override
    public Mono<Account> getAccountByEmail(String email) {
        return accountRepository.findByEmail(email).map(accountMapper::entityToAccount);
    }

    /**
     * Fetch and add the account address entity
     *
     * @param account
     * @return
     */
    private Mono<AccountEntity> getAccountAddress(AccountEntity account) {
        return addressRepository.findById(account.getAddressId())
            .map(address -> {
                account.setAddress(address);
                return account;
            });
    }

}
