package com.ycyw.graphql.service;

import java.util.List;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ycyw.graphql.entity.UserDetailEntity;
import com.ycyw.graphql.repository.AccountRepository;

import reactor.core.publisher.Mono;

/**
 * Custom reactive authentification manager to authenticate by email
 */
@Component
public class UserDetailsAuthManager implements ReactiveAuthenticationManager {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    UserDetailsAuthManager(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication auth) {
       final String email = auth.getName();
        return accountRepository.findByEmail(email)
            .map(account -> new UserDetailEntity(account.getId(), account.getEmail(), account.getPassword(), account.getRole()))
            .filter(user -> {
                return passwordEncoder.matches(auth.getCredentials().toString(), user.getPassword());
            })
            .switchIfEmpty(Mono.error(
                new BadCredentialsException("Wrong email or password")))
            .map(u -> new UsernamePasswordAuthenticationToken(u, List.of(u.getPassword(),u.getRole().toString())));
    }
}