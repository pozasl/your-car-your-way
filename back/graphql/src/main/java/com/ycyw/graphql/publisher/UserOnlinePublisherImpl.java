package com.ycyw.graphql.publisher;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;

import com.ycyw.graphql.generated.types.Role;
import com.ycyw.graphql.generated.types.UserOnline;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

@Component
public class UserOnlinePublisherImpl implements UserOnlinePublisher {

    private CopyOnWriteArrayList<UserOnline> userOnlines;
    private FluxSink<List<UserOnline>> usersStream;
    private ConnectableFlux<List<UserOnline>> usersPublisher;

    public UserOnlinePublisherImpl() {
        Flux<List<UserOnline>> publisher = Flux.create(emitter -> {
            usersStream = emitter;
        });
        usersPublisher = publisher.publish();
        usersPublisher.connect();
    }

    @Override
    public void setOnline(UserOnline user, Boolean online) {
        if (online)
            userOnlines.add(user);
        else
            userOnlines.remove(user);
        usersStream.next(userOnlines);
    }

    @Override
    public Publisher<List<UserOnline>> getUserOnlinePublisher(Role role) {
        return usersPublisher.map(users -> {
            users.removeIf(user -> !user.getRole().equals(role));
            return users;
        });
    }

}