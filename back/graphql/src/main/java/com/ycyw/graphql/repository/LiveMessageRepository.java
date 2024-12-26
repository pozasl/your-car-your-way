package com.ycyw.graphql.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.ycyw.graphql.entity.LiveMessageEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LiveMessageRepository extends ReactiveCrudRepository<LiveMessageEntity, String>{

    Mono<LiveMessageEntity> findById(Long id);

    Flux<LiveMessageEntity> findByFromUserId(long fromUserId);

    Flux<LiveMessageEntity> findByFromUserIdAndToUserId(long fromUserId, long toUserId);

    @Query("SELECT * FROM live_message WHERE from_user_id in (:user1Id, :user2Id) and to_user_id in (:user2Id, :user1Id) ORDER BY id")
    Flux<LiveMessageEntity> findBeetwenUsersOrderById(long user1Id, long user2Id);
    
}
