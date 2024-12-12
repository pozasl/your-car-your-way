package com.ycyw.graphql.mapper;

import com.ycyw.graphql.entity.UserEntity;
import com.ycyw.graphql.generated.types.NewUser;
import com.ycyw.graphql.generated.types.User;

public interface UserEntityMapper {
    
    public UserEntity newUserToEntity(NewUser user);
    public User entityToUser(UserEntity entity);
}
