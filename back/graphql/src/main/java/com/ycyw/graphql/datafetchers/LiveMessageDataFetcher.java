package com.ycyw.graphql.datafetchers;

import com.ycyw.graphql.generated.DgsConstants.MUTATION;
import com.ycyw.graphql.generated.DgsConstants.SUBSCRIPTION;
import com.ycyw.graphql.generated.types.LiveMessage;
import com.ycyw.graphql.generated.types.LiveMessageInput;
import com.ycyw.graphql.service.LiveMessageService;

import reactor.core.publisher.Mono;

import org.reactivestreams.Publisher;

import org.springframework.beans.factory.annotation.Autowired;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsSubscription;
import com.netflix.graphql.dgs.InputArgument;



/**
 * Live message graphql data fetcher for live chat
 */
@DgsComponent
public class LiveMessageDataFetcher {

    private final LiveMessageService messageService;

    @Autowired
    public LiveMessageDataFetcher(LiveMessageService messageService) {
        this.messageService = messageService;
    }

    @DgsSubscription(field = SUBSCRIPTION.NewLiveMessage)
    public Publisher<LiveMessage> newLiveMessage(@InputArgument("toUserId") String toUserId) {
        return messageService.getLiveMessagePublisher(toUserId);
    }

    @DgsMutation(field = MUTATION.SendLiveMessage)
    public Mono<LiveMessage> SendLiveMessage(@InputArgument("message") LiveMessageInput message) {
        return messageService.addMessage(message);
    }

}
