package com.ycyw.graphql.service;

import java.util.List;

import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;

import com.ycyw.graphql.entity.AccountEntity;
import com.ycyw.graphql.entity.LiveMessageEntity;
import com.ycyw.graphql.generated.types.LiveMessage;
import com.ycyw.graphql.generated.types.LiveMessageInput;
import com.ycyw.graphql.generated.types.Role;
import com.ycyw.graphql.generated.types.UserOnline;
import com.ycyw.graphql.mapper.LiveMessageEntityMapper;
import com.ycyw.graphql.publisher.LiveMessagePublisher;
import com.ycyw.graphql.publisher.UserOnlinePublisher;
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
    private final UserOnlinePublisher onlinePublisher;
    private final AccountRepository accountRepository;

    public LiveMessageServiceImpl(LiveMessageRepository messageRepository, LiveMessagePublisher messagePublisher,
            LiveMessageEntityMapper messageMapper, AccountRepository accountRepository, UserOnlinePublisher onlinePublisher) {
        this.messageRepository = messageRepository;
        this.messagePublisher = messagePublisher;
        this.messageMapper = messageMapper;
        this.accountRepository = accountRepository;
        this.onlinePublisher = onlinePublisher;
    }

    @Override
    public Publisher<LiveMessage> getLiveMessagePublisher(String accountId) {
        return messagePublisher.getLiveMessagePublisher(accountId);
    }

    @Override
    public Flux<LiveMessage> getMessageFromUserId(String accountId) {
        return messageRepository.findByFromUserId(Long.parseLong(accountId))
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
            .flatMap(savedMsg -> messageRepository.findById(savedMsg.getId()))
            .map(msgEntity -> {
                msgEntity.setFromUser(accounts.getT1());
                msgEntity.setToUser(accounts.getT2());
                return msgEntity;
            });
        
    }

    @Override
    public Publisher<List<UserOnline>> getUserOnlinePublisher(Role role) {
        return onlinePublisher.getUserOnlinePublisher(role);
    }

    @Override
    public void addUserOnline(UserOnline user) {
        onlinePublisher.setOnline(user);
    }

    @Override
    public void removeUserOnline(UserOnline user) {
        onlinePublisher.setOffline(user);;
    }

}
