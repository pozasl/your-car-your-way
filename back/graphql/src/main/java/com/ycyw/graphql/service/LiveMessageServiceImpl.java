package com.ycyw.graphql.service;

import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;

import com.ycyw.graphql.entity.AccountEntity;
import com.ycyw.graphql.entity.LiveMessageEntity;
import com.ycyw.graphql.generated.types.LiveMessage;
import com.ycyw.graphql.generated.types.LiveMessageInput;
import com.ycyw.graphql.mapper.LiveMessageEntityMapper;
import com.ycyw.graphql.publisher.LiveMessagePublisher;
import com.ycyw.graphql.repository.AccountRepository;
import com.ycyw.graphql.repository.LiveMessageRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

/**
 * Live message service implementation
 */
@Service
public class LiveMessageServiceImpl implements LiveMessageService {

    private final LiveMessageRepository messageRepository;
    private final LiveMessagePublisher messagePublisher;
    private final LiveMessageEntityMapper messageMapper;
    private final AccountRepository accountRepository;

    public LiveMessageServiceImpl(LiveMessageRepository messageRepository, LiveMessagePublisher messagePublisher,
            LiveMessageEntityMapper messageMapper, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.messagePublisher = messagePublisher;
        this.messageMapper = messageMapper;
        this.accountRepository = accountRepository;
    }

    @Override
    public Publisher<LiveMessage> getLiveMessagePublisher() {
        return messagePublisher.getLiveMessagePublisher();
    }

    @Override
    public Flux<LiveMessage> getMessageFromUserId(String userId) {
        return messageRepository.findByFromUserId(Long.parseLong(userId))
                .map(messageMapper::entityToLiveMessage);
    }

    @Override
    public Mono<LiveMessage> addMessage(LiveMessageInput message) {
        return Mono.zip(
                accountRepository.findById(Long.parseLong(message.getFromUserId())),
                accountRepository.findById(Long.parseLong(message.getToUserId())))
                .flatMap(accounts -> addUsersWithMessage(accounts, messageMapper.liveMessageToEntity(message))
                )
                .map(messageMapper::entityToLiveMessage)
                .doOnNext(msg -> {
                    messagePublisher.next(msg);
                });
    }

    private Mono<LiveMessageEntity> addUsersWithMessage(Tuple2<AccountEntity, AccountEntity> accounts, LiveMessageEntity message) {
        return messageRepository.save(message)
            // .flatMap(msg -> messageRepository.findById(msg.getId()))
            // .switchIfEmpty(Mono.error(null))
            .map(msgEntity -> {
                msgEntity.setFromUser(accounts.getT1());
                msgEntity.setToUser(accounts.getT2());
                return msgEntity;
            });
        
    }

}
