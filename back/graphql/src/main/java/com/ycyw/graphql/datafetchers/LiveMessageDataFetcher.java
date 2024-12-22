package com.ycyw.graphql.datafetchers;

import com.ycyw.graphql.generated.DgsConstants.MUTATION;
import com.ycyw.graphql.generated.DgsConstants.SUBSCRIPTION;
import com.ycyw.graphql.generated.types.LiveMessage;
import com.ycyw.graphql.generated.types.LiveMessageInput;
import com.ycyw.graphql.generated.types.OperationResult;
import com.ycyw.graphql.generated.types.Role;
import com.ycyw.graphql.generated.types.UserOnline;
import com.ycyw.graphql.service.LiveMessageService;

import reactor.core.publisher.Mono;

import java.util.List;

import org.reactivestreams.Publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

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

    @PreAuthorize("hasRole('CUSTOMER')")
    @DgsSubscription(field = SUBSCRIPTION.NewLiveMessage)
    public Publisher<LiveMessage> newLiveMessage(@InputArgument("toUserId") String toUserId) {
        return messageService.getLiveMessagePublisher(toUserId);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @DgsSubscription(field = SUBSCRIPTION.CustomerServiceOnline)
    public Publisher<List<UserOnline>> customerServiceOnline() {
        return messageService.getUserOnlinePublisher(Role.CUSTOMER_SERVICE);
    }

    @PreAuthorize("hasRole('CUSTOMER_SERVICE')")
    @DgsSubscription(field = SUBSCRIPTION.CustomersOnline)
    public Publisher<List<UserOnline>> customerOnline() {
        return messageService.getUserOnlinePublisher(Role.CUSTOMER);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @DgsMutation(field = MUTATION.SendLiveMessage)
    public Mono<LiveMessage> SendLiveMessage(@InputArgument("message") LiveMessageInput message) {
        return messageService.addMessage(message);
    }

    @PreAuthorize("isAuthenticated()")
    @DgsMutation(field = MUTATION.SetUserOnline)
    public Mono<OperationResult> setUserOnline(@InputArgument("user") UserOnline user) {
        messageService.addUserOnline(user);
        return Mono.just(OperationResult.newBuilder().message(user.getName() + "joined").build());
    }

    @PreAuthorize("isAuthenticated()")
    @DgsMutation(field = MUTATION.SetUserOffline)
    public Mono<OperationResult> setUserOffline(@InputArgument("user") UserOnline user) {
        messageService.addUserOnline(user);
        return Mono.just(OperationResult.newBuilder().message(user.getName() + "joined").build());
    }

}
