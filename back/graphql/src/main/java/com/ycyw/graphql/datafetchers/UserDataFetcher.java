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
import com.ycyw.graphql.generated.types.NewUser;
import com.ycyw.graphql.generated.types.User;
import com.ycyw.graphql.service.UserService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * User's Datafetcher
 */
@DgsComponent
public class UserDataFetcher {
    private final UserService userService;

    @Autowired
    public UserDataFetcher(UserService userService) {
        this.userService = userService;
    }

    /**
     * Get a user by its id
     *
     * @param id
     * @return
     */
    @DgsData (
        parentType = DgsConstants.QUERY_TYPE,
        field = QUERY.UserById
    )
    public Mono<User> userById(@InputArgument("id") String id) {
        if (Strings.isBlank(id)) {
            new RuntimeException("Invalid Product ID.");
        }
        return userService.getUser(id);
    }

    /**
     * Get all users
     * @return
     */
    @DgsData (
        parentType = DgsConstants.QUERY_TYPE,
        field = QUERY.Users
    )
    public Flux<User> users() {
        return userService.getUsers();
    }

    /**
     * Add a new user
     * @param user
     * @return
     */
    @DgsMutation(
        field = MUTATION.AddUser
    )
    public Mono<User> addUser(@InputArgument("user") NewUser user) {
        return userService.createUser(user);
    }


}
