package com.ycyw.graphql.service;

import com.ycyw.graphql.generated.types.NewCustomerAccountInput;
import com.ycyw.graphql.generated.types.Account;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Account's service
 */
public interface AccountService {
    /**
     * Get a Account by its id
     *
     * @param id the user's id
     * @return a user's mono
     */
    Mono<Account> getAccount(String id);

    /**
     * Get a Account by its email
     *
     * @param email the user's email
     * @return a user's mono
     */
    Mono<Account> getAccountByEmail(String email);

    /**
     * Create or Update a customer account
     *
     * @param user The user to create/update
     * @return the saved user;
     */
    Mono<Account> createAccount(NewCustomerAccountInput user);

    /**
     * Get All accounts
     * 
     * @return Accounts' flux
     */
    Flux<Account> getAccounts();
}
