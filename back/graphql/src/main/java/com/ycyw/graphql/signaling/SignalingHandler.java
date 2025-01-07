package com.ycyw.graphql.signaling;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

/**
 * Simple reactive Signaling handler implementation for WebRTC 
 */
@Component("SignalingHandler")
public class SignalingHandler implements WebSocketHandler {

    private FluxSink<SignalMessage> messagesStream;
    private ConnectableFlux<SignalMessage> messagesPublisher;

    public SignalingHandler() {
        Flux<SignalMessage> publisher = Flux.create(emitter -> {
            messagesStream = emitter;
        });
        messagesPublisher = publisher.publish();
        messagesPublisher.connect();
    }

    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        return webSocketSession.send(messagesPublisher
            .filter(s -> s.getSessionId() != webSocketSession.getId())
            .map(signal -> signal.getContent())
            .map(webSocketSession::textMessage)
        ).and(webSocketSession.receive()
            .map(WebSocketMessage::getPayloadAsText).log().map(
                msg -> messagesStream.next(
                    SignalMessage.builder()
                    .content(msg)
                    .sessionId(webSocketSession.getId())
                    .build()
                )
            )
        );
    }

}