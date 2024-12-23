package com.ycyw.graphql.publisher;

import java.util.List;

import org.reactivestreams.Publisher;

import com.ycyw.graphql.generated.types.Role;
import com.ycyw.graphql.generated.types.UserOnline;

import reactor.core.publisher.Flux;

/**
 * Publish the users online list
 */
public interface UserOnlinePublisher {

    /**
     * Set a user online
     * @param user
     */
    void setOnline(UserOnline user, Boolean online);

    /**
     * Get a specific role's users online publisher
     * @param role
     * @return The filtered publisher
     */
    Publisher<List<UserOnline>> getUserOnlinePublisher(Role role);

    /**
     * Get users online with role
     * @param role
     * @return
     */
    Flux<UserOnline> getUsersOnlineWithRole(Role role);
    
}
