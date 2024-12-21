package com.ycyw.graphql.datafetchers;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import com.ycyw.graphql.generated.DgsConstants;
import com.ycyw.graphql.generated.DgsConstants.MUTATION;
import com.ycyw.graphql.generated.DgsConstants.QUERY;
import com.ycyw.graphql.generated.types.AccountCredentials;
import com.ycyw.graphql.generated.types.NewCustomerAccountInput;
import com.ycyw.graphql.generated.types.OperationResult;
import com.ycyw.graphql.service.AccountService;

import reactor.core.publisher.Mono;

/**
 * Authentication's Datafetcher
 */
@DgsComponent
public class AuthenticationDataFetcher {

    private AccountService accountService;

    public AuthenticationDataFetcher(AccountService accountService) {
        this.accountService = accountService;
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

    /**
     * Query account token with credentials
     */
    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = QUERY.Token)
    public Mono<String> getToken(@InputArgument("credentials") AccountCredentials credentials) {
        return null;
    }
}
