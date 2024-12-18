package com.ycyw.graphql.mapper;

import java.time.ZoneOffset;

import org.springframework.stereotype.Component;

import com.ycyw.graphql.entity.LiveMessageEntity;
import com.ycyw.graphql.generated.types.LiveMessage;
import com.ycyw.graphql.generated.types.LiveMessageInput;

@Component
public class LiveMessageEntityMapperImpl implements LiveMessageEntityMapper{

    private final AccountEntityMapper accountMapper;

    public LiveMessageEntityMapperImpl(AccountEntityMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    @Override
    public LiveMessageEntity liveMessageToEntity(LiveMessageInput message) {
        return LiveMessageEntity.builder()
            .fromUserId(Long.parseLong(message.getFromUserId()))
            .toUserId(Long.parseLong(message.getToUserId()))
            .content(message.getContent())
            .build();
    }

    @Override
    public LiveMessage entityToLiveMessage(LiveMessageEntity entity) {
        return LiveMessage.newBuilder()
            .from(accountMapper.entityToAccount(entity.getFromUser()))
            .to(accountMapper.entityToAccount(entity.getToUser()))
            .at(entity.getCreatedAt().atOffset(ZoneOffset.UTC))
            .content(entity.getContent())
            .build();
    }
    
}
