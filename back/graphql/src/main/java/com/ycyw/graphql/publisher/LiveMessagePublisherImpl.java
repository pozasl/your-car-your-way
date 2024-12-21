package com.ycyw.graphql.publisher;

import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;

import com.ycyw.graphql.generated.types.LiveMessage;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

@Component
public class LiveMessagePublisherImpl implements LiveMessagePublisher{

    private FluxSink<LiveMessage> messagesStream;
    private ConnectableFlux<LiveMessage> messagesPublisher;

    public LiveMessagePublisherImpl() {
        Flux<LiveMessage> publisher = Flux.create(emitter -> {
            messagesStream = emitter;
        });
        messagesPublisher = publisher.publish();
        messagesPublisher.connect();
    }

    @Override
    public void next(LiveMessage message) {
        messagesStream.next(message);
    }

    @Override
    public Publisher<LiveMessage> getLiveMessagePublisher(String accountId) {
        return messagesPublisher.filter(msg -> msg.getTo().getId().equals(accountId));
    };
    
}
