package com.ycyw.graphql.datafetchers;

import com.ycyw.graphql.generated.types.LiveMessage;
import com.ycyw.graphql.generated.types.LiveMessageInput;
import com.ycyw.graphql.generated.types.OperationResult;
import com.ycyw.graphql.generated.types.Role;
import com.ycyw.graphql.generated.types.UserOnline;
import com.ycyw.graphql.service.LiveMessageService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import org.reactivestreams.Publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

/**
 * Live message graphql data fetcher for live chat
 */
@Controller
public class LiveMessageDataFetcher {

    private final LiveMessageService messageService;

    @Autowired
    public LiveMessageDataFetcher(LiveMessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Live Chat message subscription
     *
     * @param toUserId
     * @return
     */
    @PreAuthorize("isAuthenticated()")
    @SubscriptionMapping
    public Publisher<LiveMessage> newLiveMessage(@Argument String toUserId) {
        return messageService.getLiveMessagePublisher(toUserId);
    }

    /**
     * Live Chat Customer Service users online subscription
     *
     * @return
     */
    @PreAuthorize("hasAuthority('SCOPE_CUSTOMER')")
    @SubscriptionMapping
    public Publisher<List<UserOnline>> customerServiceOnline() {
        return messageService.getUserOnlinePublisher(Role.CUSTOMER_SERVICE);
    }


    /**
     * Live Chat Customer users online subscription
     *
     * @return
     */
    @PreAuthorize("hasAuthority('SCOPE_CUSTOMER_SERVICE')")
    @SubscriptionMapping
    public Publisher<List<UserOnline>> customersOnline() {
        return messageService.getUserOnlinePublisher(Role.CUSTOMER);
    }

    /**
     * Send a live chat message
     *
     * @param message
     * @return
     */
    @PreAuthorize("isAuthenticated()")
    @MutationMapping
    public Mono<LiveMessage> sendLiveMessage(@Argument LiveMessageInput message) {
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
    @MutationMapping
    public Mono<OperationResult> setUserOnline(@Argument UserOnline user, @Argument Boolean online) {
        messageService.setUserOnline(user, online);
        String msg = online ? "joined" : "leaved";
        return Mono.just(OperationResult.newBuilder().message(String.format("User %s %s", user.getName(), msg)).build());
    }

    /**
     * Get user onlines with role
     * @param role the User role
     * @return Filtered Online Users flux
     */
    @PreAuthorize("isAuthenticated()")
    @QueryMapping
    public Flux<UserOnline> usersOnline(@Argument Role role) {
        return messageService.getUsersOnlineWithRole(role);
    }


    /**
     * Get recorded live messages between two users
     *
     * @param customerId The customer id
     * @param customerServiceId The customer service staff id
     * @return The messages list
     */
    @PreAuthorize("isAuthenticated()")
    @QueryMapping
    public Flux<LiveMessage> liveMessages(@Argument String customerId, @Argument String customerServiceId) {
        return messageService.getMessageBetween(customerId, customerServiceId);
    }

}
