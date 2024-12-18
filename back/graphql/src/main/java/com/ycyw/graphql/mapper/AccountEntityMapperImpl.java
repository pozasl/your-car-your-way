package com.ycyw.graphql.mapper;


import org.springframework.stereotype.Component;

import com.ycyw.graphql.entity.AccountEntity;
import com.ycyw.graphql.generated.types.NewCustomerAccountInput;
import com.ycyw.graphql.generated.types.Role;
import com.ycyw.graphql.generated.types.Account;

@Component
public class AccountEntityMapperImpl implements AccountEntityMapper{

    private final AddressEntityMapper addressMapper;

    public AccountEntityMapperImpl(AddressEntityMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    @Override
    public AccountEntity newAccountToEntity(NewCustomerAccountInput account) {
        return AccountEntity.builder()
            .email(account.getEmail())
            .title(account.getTitle())
            .firstname(account.getFirstName())
            .lastname(account.getLastName())
            .password(account.getPassword())
            .birthDate(account.getBirthDate())
            .address(addressMapper.newAddressToEntity(account.getAddress()))
            .role(Role.CUSTOMER)
            .build();
    }

    @Override
    public Account entityToAccount(AccountEntity entity) {
        return Account.newBuilder()
            .id(entity.getId().toString())
            .title(entity.getTitle())
            .email(entity.getEmail())
            .firstName(entity.getFirstname())
            .lastName(entity.getLastname())
            .birthDate(entity.getBirthDate())
            .address(addressMapper.entityToAddress(entity.getAddress()))
            .role(entity.getRole())
            .build(); 
    }
    
}
