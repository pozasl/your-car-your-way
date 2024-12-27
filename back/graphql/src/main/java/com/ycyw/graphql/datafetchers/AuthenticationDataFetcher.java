package com.ycyw.graphql.datafetchers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.bind.annotation.RequestHeader;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import com.ycyw.graphql.generated.DgsConstants;
import com.ycyw.graphql.generated.DgsConstants.MUTATION;
import com.ycyw.graphql.generated.DgsConstants.QUERY;
import com.ycyw.graphql.generated.types.Account;
import com.ycyw.graphql.generated.types.AccountCredentials;
import com.ycyw.graphql.generated.types.NewCustomerAccountInput;
import com.ycyw.graphql.generated.types.OperationResult;
import com.ycyw.graphql.service.AccountService;
import com.ycyw.graphql.service.JwtService;
import com.ycyw.graphql.service.UserDetailsAuthManager;

import reactor.core.publisher.Mono;

/**
 * Authentication's Datafetcher
 */
@DgsComponent
public class AuthenticationDataFetcher {

    private AccountService accountService;
    private UserDetailsAuthManager authManager;
    private JwtService jwtService;

    public AuthenticationDataFetcher(AccountService accountService, UserDetailsAuthManager authManager,
            JwtService jwtService) {
        this.accountService = accountService;
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    /**
     * Add a new account
     * 
     * @param account
     * @return The register operation's result
     */
    @DgsMutation(field = MUTATION.RegisterCustomer)
    public Mono<OperationResult> registerCustomer(@InputArgument("account") NewCustomerAccountInput account) {
        return accountService.createAccount(account)
                .then(Mono.just(OperationResult.newBuilder().message("Account registered").build()));
    }

    /**
     * Query account token with credentials (Aka Login)
     *
     * @param credentials The account's credentials
     * @return A JWT token
     */
    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = QUERY.Token)
    public Mono<String> getToken(
            @InputArgument("credentials") AccountCredentials credentials,
            @RequestHeader("Content-Type") String contentType) {
        return authManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword()))
                .map(jwtService::generateToken);
    }

    /**
     * Query user's account info based on JWT auth
     * 
     * @return Account info
     */
    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = QUERY.Me)
    @PreAuthorize("isAuthenticated()")
    public Mono<Account> getMe() {
        // No @AuthenticationPrincipal param anotation with DGS
        return ReactiveSecurityContextHolder.getContext()
            .map(m->m.getAuthentication())
            .flatMap(auth -> accountService.getAccountByEmail(auth.getName()));
    }
}
