package com.ycyw.graphql.service;

import java.util.List;

import org.reactivestreams.Publisher;

import com.ycyw.graphql.generated.types.LiveMessage;
import com.ycyw.graphql.generated.types.LiveMessageInput;
import com.ycyw.graphql.generated.types.Role;
import com.ycyw.graphql.generated.types.UserOnline;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Live message sevice
 */
public interface LiveMessageService {

    /**
     * Get LiveMessage Publisher
     * 
     * @param accountId User's account id
     * @return A live message publisher filtered by user's destination
     */
    Publisher<LiveMessage> getLiveMessagePublisher(String accountId);

    /**
     * Get user' messages
     * 
     * @param accountId User's account id
     * @return All live messages Flux
     */
    Flux<LiveMessage> getMessageFromUserId(String accountId);

    /**
     * Record a new live message
     * 
     * @param message Live message to record
     * @return Recorded live message Mono
     */
    Mono<LiveMessage> addMessage(LiveMessageInput message);

    /**
     * Get Users online publisher filtered by Role
     *
     * @param role The users role
     * @return Users online list
     */
    Publisher<List<UserOnline>> getUserOnlinePublisher(Role role);

    /**
     * Add a user in the online user list
     * @param user the user to set online
     * @return
     */
    void addUserOnline(UserOnline user);

    /**
     * Remove user in the online user list
     * @param user the user to set online
     * @return
     */
    void removeUserOnline(UserOnline user);
    
}
