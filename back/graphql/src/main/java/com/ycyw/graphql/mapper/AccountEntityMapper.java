package com.ycyw.graphql.mapper;

import com.ycyw.graphql.entity.AccountEntity;
import com.ycyw.graphql.generated.types.Account;
import com.ycyw.graphql.generated.types.NewCustomerAccountInput;

/**
 * Convert Account entity
 */
public interface AccountEntityMapper {
    
    /**
     * Convert New acccount model to account entity
     *
     * @param account The New account model
     * @return An acccount entity
     */
    public AccountEntity newAccountToEntity(NewCustomerAccountInput account);

    /**
     * Convert account entity to account model
     *
     * @param entity The account entity
     * @return An account model
     */
    public Account entityToAccount(AccountEntity entity);
}
