package com.ycyw.graphql.publisher;

import java.util.ArrayList;
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

    private CopyOnWriteArrayList<UserOnline> userOnlines = new CopyOnWriteArrayList<>();
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
        if (online) {
            userOnlines.addIfAbsent(user);
        } else {
            userOnlines.remove(user);
        }
        // userOnlines.forEach(u -> System.out.println(String.format("== [%s]", u.getId())));
        usersStream.next(userOnlines);

    }

    @Override
    public Publisher<List<UserOnline>> getUserOnlinePublisher(Role role) {
        return usersPublisher.map(users -> filterUsersByRole(users, role));
    }

    @Override
    public Flux<UserOnline> getUsersOnlineWithRole(Role role) {
        return Flux.fromIterable(filterUsersByRole(userOnlines, role));
    }

    private ArrayList<UserOnline> filterUsersByRole(List<UserOnline> users, Role role) {
        ArrayList<UserOnline> tmp = new ArrayList<>(users);
        tmp.removeIf(user -> !user.getRole().equals(role));
        return tmp;
    }

}
