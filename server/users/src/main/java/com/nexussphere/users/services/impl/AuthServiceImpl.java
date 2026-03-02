package com.nexussphere.users.services.impl;

import com.nexussphere.common.exceptions.AccountNotEnabledException;
import com.nexussphere.users.dtos.requests.UserCredentials;
import com.nexussphere.users.exception.InvalidCredentialException;
import com.nexussphere.users.exception.UserNotFoundException;
import com.nexussphere.users.models.Users;
import com.nexussphere.users.repository.UsersRepository;
import com.nexussphere.users.services.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.security.auth.login.AccountLockedException;

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
