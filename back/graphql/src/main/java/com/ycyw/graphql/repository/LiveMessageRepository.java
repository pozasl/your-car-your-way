package com.ycyw.graphql.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.ycyw.graphql.entity.LiveMessageEntity;

import reactor.core.publisher.Flux;

public interface LiveMessageRepository extends ReactiveCrudRepository<LiveMessageEntity, String>{

    Flux<LiveMessageEntity> findByFromUserId(long fromUserId);
    
}
