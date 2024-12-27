package com.ycyw.graphql.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.ycyw.graphql.entity.LiveMessageEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LiveMessageRepository extends ReactiveCrudRepository<LiveMessageEntity, String>{

    /**
     * Find live message by Id
     * @param id
     * @return
     */
    Mono<LiveMessageEntity> findById(Long id);

    /**
     * Find live messages exchanged between users by their user id by desc order
     *
     * @param user1Id A user id
     * @param user2Id The other user id
     * @return List of live messages as Flux
     */
    @Query("SELECT * FROM live_message WHERE from_user_id in (:user1Id, :user2Id) and to_user_id in (:user2Id, :user1Id) ORDER BY id DESC")
    Flux<LiveMessageEntity> findBeetwenUsersOrderByIdDesc(long user1Id, long user2Id);
    
}
