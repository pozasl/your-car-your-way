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

/**
 * UserOnlinePublisher implementation
 */
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

    /**
     * Filter users online by their role
     * @param users The users online
     * @param role The role
     * @return The filtered users
     */
    private ArrayList<UserOnline> filterUsersByRole(List<UserOnline> users, Role role) {
        ArrayList<UserOnline> tmp = new ArrayList<>(users);
        tmp.removeIf(user -> !user.getRole().equals(role));
        return tmp;
    }

}
