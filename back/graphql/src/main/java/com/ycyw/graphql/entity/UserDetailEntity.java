package com.ycyw.graphql.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ycyw.graphql.generated.types.Role;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * Extended User Detail entity
 */
@Data
@Builder
public class UserDetailEntity implements UserDetails {

    @Id
    private Long id;
    @NonNull
    private String email;
    @NonNull
    private String password;
    @NonNull
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + getRole().toString()));
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

}