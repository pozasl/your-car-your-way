package com.ycyw.graphql.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.ycyw.graphql.entity.LiveMessageEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LiveMessageRepository extends ReactiveCrudRepository<LiveMessageEntity, String>{

    Mono<LiveMessageEntity> findById(Long id);

    Flux<LiveMessageEntity> findByFromUserId(long fromUserId);

    Flux<LiveMessageEntity> findByFromUserIdAndToUserId(long fromUserId, long toUserId);
    
}
