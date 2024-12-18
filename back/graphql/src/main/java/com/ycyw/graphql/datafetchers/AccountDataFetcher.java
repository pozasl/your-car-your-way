package com.ycyw.graphql.datafetchers;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import com.ycyw.graphql.generated.DgsConstants;
import com.ycyw.graphql.generated.DgsConstants.MUTATION;
import com.ycyw.graphql.generated.DgsConstants.QUERY;
import com.ycyw.graphql.generated.types.NewCustomerAccountInput;
import com.ycyw.graphql.generated.types.OperationResult;
import com.ycyw.graphql.service.AccountService;
import com.ycyw.graphql.generated.types.Account;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Account's Datafetcher
 */
@DgsComponent
public class AccountDataFetcher {
    private final AccountService accountService;

    @Autowired
    public AccountDataFetcher(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Get an account by its id
     *
     * @param id
     * @return
     */
    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = QUERY.Account)
    public Mono<Account> account(@InputArgument("id") String id) {
        if (Strings.isBlank(id)) {
            new RuntimeException("Invalid account ID.");
        }
        return accountService.getAccount(id);
    }

    /**
     * Get all accounts
     * 
     * @return
     */
    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = QUERY.Accounts)
    public Flux<Account> accounts() {
        return accountService.getAccounts();
    }

    /**
     * Add a new account
     * 
     * @param account
     * @return
     */
    @DgsMutation(field = MUTATION.RegisterCustomer)
    public Mono<OperationResult> registerCustomer(@InputArgument("account") NewCustomerAccountInput account) {
        return accountService.createAccount(account)
                .then(Mono.just(OperationResult.newBuilder().message("Account registered").build()));
    }

}
