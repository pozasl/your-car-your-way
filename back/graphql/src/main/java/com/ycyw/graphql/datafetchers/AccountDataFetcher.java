package com.ycyw.graphql.datafetchers;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import com.ycyw.graphql.generated.types.Account;
import com.ycyw.graphql.service.AccountService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Account's Datafetcher
 */
@Controller
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
    @PreAuthorize("hasAuthority('SCOPE_CUSTOMER_SERVICE')")
    @QueryMapping
    public Mono<Account> account(@Argument String id) {
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
    @PreAuthorize("hasAuthority('SCOPE_CUSTOMER_SERVICE')")
    @QueryMapping
    public Flux<Account> accounts() {
        return accountService.getAccounts();
    }

}
