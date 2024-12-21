package com.ycyw.graphql.mapper;

import com.ycyw.graphql.entity.LiveMessageEntity;
import com.ycyw.graphql.generated.types.LiveMessage;
import com.ycyw.graphql.generated.types.LiveMessageInput;

public interface LiveMessageEntityMapper {

    LiveMessageEntity liveMessageToEntity(LiveMessageInput message);

    LiveMessage entityToLiveMessage(LiveMessageEntity entity);

}