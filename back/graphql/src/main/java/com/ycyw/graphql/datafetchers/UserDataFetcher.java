package com.ycyw.graphql.datafetchers;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import com.ycyw.graphql.generated.DgsConstants;
import com.ycyw.graphql.generated.DgsConstants.QUERY;
import com.ycyw.graphql.generated.types.User;
import com.ycyw.graphql.service.UserService;

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
     * userById query
     * @param id
     * @return
     */
    @DgsData (
        parentType = DgsConstants.QUERY_TYPE,
        field = QUERY.UserById
    )
    public Mono<User> cc(@InputArgument("id") String id) {
        if (Strings.isBlank(id)) {
            new RuntimeException("Invalid Product ID.");
        }
        return userService.getUser(id);
    }
}
