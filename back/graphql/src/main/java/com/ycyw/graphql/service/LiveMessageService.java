package com.ycyw.graphql.service;

import org.reactivestreams.Publisher;

import com.ycyw.graphql.generated.types.LiveMessage;
import com.ycyw.graphql.generated.types.LiveMessageInput;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Live message sevice
 */
public interface LiveMessageService {

    /**
     * Get LiveMessagePublisher
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
    
}
