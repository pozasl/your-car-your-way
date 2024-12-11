package com.ycyw.graphql.service;

import com.ycyw.graphql.generated.types.NewUser;
import com.ycyw.graphql.generated.types.User;

import reactor.core.publisher.Mono;

/**
 * User's service
 */
public interface UserService {
    /**
     * Get a User by its id
     *
     * @param id the user's id
     * @return a user's mono
     */
    Mono<User> getUser(String id);

    /**
     * Create or Update a user
     *
     * @param user The user to create/update
     * @return the saved user;
     */
    Mono<User> createUser(NewUser user);
}
