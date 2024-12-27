package com.ycyw.graphql.mapper;

import com.ycyw.graphql.entity.LiveMessageEntity;
import com.ycyw.graphql.generated.types.LiveMessage;
import com.ycyw.graphql.generated.types.LiveMessageInput;

/**
 * Live message entity mapper
 */
public interface LiveMessageEntityMapper {

    /**
     * Convert LiveMessage to LiveMessageEntity
     * 
     * @param message The Model
     * @return The entity
     */
    LiveMessageEntity liveMessageToEntity(LiveMessageInput message);

    /**
     * Convert LiveMessageEntity to LiveMessage
     *
     * @param entity The entity
     * @return The model
     */
    LiveMessage entityToLiveMessage(LiveMessageEntity entity);

}