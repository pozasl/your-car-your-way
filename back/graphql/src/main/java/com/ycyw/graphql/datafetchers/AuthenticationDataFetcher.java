package com.ycyw.graphql.datafetchers;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Controller;

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
@Controller
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
    @MutationMapping
    public Mono<OperationResult> registerCustomer(@Argument NewCustomerAccountInput account) {
        return accountService.createAccount(account)
                .then(Mono.just(OperationResult.newBuilder().message("Account registered").build()));
    }

    /**
     * Query account token with credentials (Aka Login)
     *
     * @param credentials The account's credentials
     * @return A JWT token
     */
    @QueryMapping
    public Mono<String> token(@Argument AccountCredentials credentials) {
        return authManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword()))
                .map(jwtService::generateToken);
    }

    /**
     * Query user's account info based on JWT auth
     * 
     * @return Account info
     */
    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public Mono<Account> me() {
        // No @AuthenticationPrincipal param anotation with DGS
        return ReactiveSecurityContextHolder.getContext()
            .map(m->m.getAuthentication())
            .flatMap(auth -> accountService.getAccountByEmail(auth.getName()));
    }
}
