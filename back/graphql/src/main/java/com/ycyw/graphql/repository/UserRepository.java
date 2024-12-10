package com.ycyw.graphql.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.ycyw.graphql.generated.types.User;

import reactor.core.publisher.Mono;

/**
 * User's reactive repository
 */
@Repository
public interface UserRepository extends ReactiveCrudRepository<User, String>{
    /**
     * Find a user by email
     *
     * @param email User's email
     * @return User detail entity
     */
    Mono<User> findByEmail(String email);
}