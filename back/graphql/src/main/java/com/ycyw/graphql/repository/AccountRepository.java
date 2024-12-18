package com.ycyw.graphql.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.ycyw.graphql.entity.AccountEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
/**
 * Account's reactive repository
 */
public interface AccountRepository extends ReactiveCrudRepository<AccountEntity, Long> {

    /**
     * Find an account by email
     *
     * @param email Account's email
     * @return Account mono
     */
    Mono<AccountEntity> findByEmail(String email);


    /**
     * Find all accounts
     *
     * @return Accounts' flux
     */
    Flux<AccountEntity> findAll();
 
    /**
     * Find an account by email
     *
     * @param email Account's email
     * @return Account mono
     */
    Mono<AccountEntity> save(AccountEntity account);
}