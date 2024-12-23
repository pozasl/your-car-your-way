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

    // TODO: Fix @PreAuthorize with @DgsSubscription

    /**
     * Live Chat message subscription
     *
     * @param toUserId
     * @return
     */
    // @PreAuthorize("isAuthenticated()")
    @DgsSubscription(field = SUBSCRIPTION.NewLiveMessage)
    public Publisher<LiveMessage> newLiveMessage(@InputArgument("toUserId") String toUserId) {
        System.out.println(String.format("-> user %s subs to messages", toUserId));
        return messageService.getLiveMessagePublisher(toUserId);
    }

    /**
     * Live Chat Customer Service users online subscription
     *
     * @return
     */
    // @PreAuthorize("hasRole('CUSTOMER')")
    @DgsSubscription(field = SUBSCRIPTION.CustomerServiceOnline)
    public Publisher<List<UserOnline>> customerServiceOnline() {
        return messageService.getUserOnlinePublisher(Role.CUSTOMER_SERVICE);
    }


    /**
     * Live Chat Customer users online subscription
     *
     * @return
     */
    // @PreAuthorize("hasRole('CUSTOMER_SERVICE')")
    @DgsSubscription(field = SUBSCRIPTION.CustomersOnline)
    public Publisher<List<UserOnline>> customerOnline() {
        return messageService.getUserOnlinePublisher(Role.CUSTOMER);
    }

    /**
     * Send a live chat message
     *
     * @param message
     * @return
     */
    @PreAuthorize("isAuthenticated()")
    @DgsMutation(field = MUTATION.SendLiveMessage)
    public Mono<LiveMessage> SendLiveMessage(@InputArgument("message") LiveMessageInput message) {
        System.out.println(String.format("-> in coming message from %s", message.getFromUserId()));
        return messageService.addMessage(message);
    }

    /**
     * Set user online status
     *
     * @param user
     * @param online
     * @return
     */
    @PreAuthorize("isAuthenticated()")
    @DgsMutation(field = MUTATION.SetUserOnline)
    public Mono<OperationResult> setUserOnline(@InputArgument("user") UserOnline user, @InputArgument("online") Boolean online) {
        messageService.setUserOnline(user, online);
        return Mono.just(OperationResult.newBuilder().message(user.getName() + "joined").build());
    }


}
