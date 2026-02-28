package com.intralogix.users.services.impl;

import com.intralogix.common.dtos.AuthToken;

import com.intralogix.common.exceptions.AccountNotEnabledException;
import com.intralogix.common.jwt.JwtService;
import com.intralogix.users.dtos.requests.UserCredentials;
import com.intralogix.users.exception.InvalidCredentialException;
import com.intralogix.users.exception.UserNotFoundException;
import com.intralogix.users.models.Users;
import com.intralogix.users.repository.UsersRepository;
import com.intralogix.users.services.AuthService;
import com.intralogix.users.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.security.auth.login.AccountLockedException;
import java.util.HashMap;
import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public Mono<Users> validateUser(UserCredentials credentials) {
        Mono<Users> usersMono = usersRepository.findByEmailOrUsername(credentials.emailOrUsername());

        return usersMono
                .onErrorResume((err) -> {
                    log.error(err.getMessage());
                    return Mono.error(new RuntimeException("Database exception."));
                })
                .flatMap((users) -> {
                    boolean isPasswordMatches = passwordEncoder.matches(credentials.password(), users.getPassword());
                    if (!isPasswordMatches) {
                        log.error("Found invalid password for user: {}", credentials.emailOrUsername());
                        return Mono.error(new InvalidCredentialException("Invalid username or password"));
                    }
                    if (!users.getIsAccountEnabled()) {
                        log.warn("Account not enabled for user: {}", credentials.emailOrUsername());
                        return Mono.error(new AccountNotEnabledException(credentials.emailOrUsername(), "Account not enabled."));
                    }

                    if (users.getIsAccountLocked()) {
                        log.warn("Account locked for user: {}", credentials.emailOrUsername());
                        return Mono.error(new AccountLockedException("Account locked, contact to administrator."));
                    }
                    return Mono.just(users);
                })
                .switchIfEmpty(Mono.error(() -> {
                    log.error("User not exists with credential: {}", credentials.emailOrUsername());
                    return new UserNotFoundException("Invalid username.");
                }));
    }
/*
    @Override
    public AuthToken login(UserCredentials userCredentials, Boolean rememberMe) {
        final Users users;
        try {
            users = userService.findUserByEmailOrUsername(userCredentials.usernameOrEmail());
        } catch (NoSuchElementException e) {
            String msg = String.format("User not found with username %s", userCredentials.usernameOrEmail());
            log.error(msg);
            throw new UserNotFoundException(msg);
        }

        if (!passwordEncoder.matches(userCredentials.password(), users.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }
        return jwtService.generateAuthorization(users.getId(), rememberMe);
    }

    @Override
    public String refreshAccessToken(String userId) {
        final Users users;
        try{
            users = userService.findUserById(userId);
        }catch (NoSuchElementException e){
            log.error("User not found with user id {}", userId);
            throw new UserNotFoundException("User not found");
        }
        if(!users.isEnabled()){
            throw new AccountNotEnabledException(userId);
        }
        return jwtService.generateAccessToken(users, new HashMap<>());
    }*/
}
