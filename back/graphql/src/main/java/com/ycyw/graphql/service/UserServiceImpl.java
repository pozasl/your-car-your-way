package com.ycyw.graphql.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ycyw.graphql.generated.types.Address;
import com.ycyw.graphql.generated.types.NewAddress;
import com.ycyw.graphql.generated.types.NewUser;
import com.ycyw.graphql.generated.types.Role;
import com.ycyw.graphql.generated.types.User;
import com.ycyw.graphql.repository.UserRepository;

import reactor.core.publisher.Mono;

/**
 * User's service implementation
 */
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<User> getUser(String id) {
        return userRepository.findById(id);
    }

    @Override
    public Mono<User> createUser(NewUser user) {
        NewAddress address = user.getAddress();
        User userToSave = User.newBuilder()
            .email(user.getEmail())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .password(user.getPassword())
            .birthDate(user.getBirthDate())
            .address(Address.newBuilder()
                .street(address.getStreet())
                .city(address.getCity())
                .zipcode(address.getZipcode())
                .state(address.getState())
                .country(address.getCountry())
                .build()
            )
            .roles(List.of(Role.USER))
            .build();
        return this.userRepository.save(userToSave);
    }
    
}
