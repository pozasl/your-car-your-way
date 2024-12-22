package com.ycyw.graphql.publisher;

import java.util.List;

import org.reactivestreams.Publisher;

import com.ycyw.graphql.generated.types.Role;
import com.ycyw.graphql.generated.types.UserOnline;

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
     * @return
     */
    Publisher<List<UserOnline>> getUserOnlinePublisher(Role role);
    
}
