package com.ycyw.graphql.mapper;

import com.ycyw.graphql.entity.AccountEntity;
import com.ycyw.graphql.generated.types.Account;
import com.ycyw.graphql.generated.types.NewCustomerAccountInput;

public interface AccountEntityMapper {
    
    public AccountEntity newAccountToEntity(NewCustomerAccountInput account);
    public Account entityToAccount(AccountEntity entity);
}
