package com.ycyw.graphql.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ycyw.graphql.entity.UserEntity;
import com.ycyw.graphql.generated.types.NewUser;
import com.ycyw.graphql.generated.types.User;
import com.ycyw.graphql.mapper.UserEntityMapper;
import com.ycyw.graphql.repository.UserRepository;

import reactor.core.publisher.Mono;

/**
 * User's service implementation
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserEntityMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserEntityMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Mono<User> getUser(String id) {
        return userRepository.findById(id).map(userMapper::entityToUser);
    }

    @Override
    public Mono<User> createUser(NewUser user) {
        UserEntity userToSave = userMapper.newUserToEntity(user);
        return this.userRepository.save(userToSave).map(userMapper::entityToUser);
    }

}
