package com.ycyw.graphql.repository;

import com.ycyw.graphql.generated.types.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * User's reactive repository
 */
public interface UserRepository {
    /**
     * Find a user by id
     *
     * @param id User's id
     * @return User mono
     */
    Mono<User> findById(String id);

    /**
     * Find a user by email
     *
     * @param email User's email
     * @return User mono
     */
    Mono<User> findByEmail(String email);


    /**
     * Find all users
     *
     * @return Users flux
     */
    Flux<User> findAll();
 
    /**
     * Find a user by email
     *
     * @param email User's email
     * @return User mono
     */
    Mono<User> save(User user);
}