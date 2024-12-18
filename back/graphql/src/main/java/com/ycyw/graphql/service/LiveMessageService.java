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
     * @return the live message publisher
     */
    Publisher<LiveMessage> getLiveMessagePublisher();

    Flux<LiveMessage> getMessageFromUserId(String userId);

    Mono<LiveMessage> addMessage(LiveMessageInput message);
    
}
