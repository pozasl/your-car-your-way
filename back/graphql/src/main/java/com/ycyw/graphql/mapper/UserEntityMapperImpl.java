package com.ycyw.graphql.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ycyw.graphql.entity.UserEntity;
import com.ycyw.graphql.generated.types.NewUser;
import com.ycyw.graphql.generated.types.Role;
import com.ycyw.graphql.generated.types.User;

@Component
public class UserEntityMapperImpl implements UserEntityMapper{

    private final AddressEntityMapper addressMapper;

    public UserEntityMapperImpl(AddressEntityMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    @Override
    public UserEntity newUserToEntity(NewUser user) {
        return UserEntity.builder()
            .email(user.getEmail())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .password(user.getPassword())
            .birthDate(user.getBirthDate())
            .address(addressMapper.newAddressToEntity(user.getAddress()))
            .roles(List.of(Role.USER))
            .build();
    }

    @Override
    public User entityToUser(UserEntity entity) {
        return User.newBuilder()
            .id(entity.getId().toString())
            .email(entity.getEmail())
            .firstName(entity.getFirstName())
            .lastName(entity.getLastName())
            .password(entity.getPassword())
            .birthDate(entity.getBirthDate())
            .address(addressMapper.entityToAddress(entity.getAddress()))
            .roles(entity.getRoles())
            .build(); 
    }
    
}
