package com.ycyw.graphql.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    
}
