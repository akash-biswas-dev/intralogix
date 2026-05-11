package com.biswasakashdev.nexussphere.users.services.impl;

import com.biswasakashdev.nexussphere.users.dtos.requests.UserCredentials;
import com.biswasakashdev.nexussphere.users.exception.InvalidCredentialException;
import com.biswasakashdev.nexussphere.users.exception.ProfileNotCompleteException;
import com.biswasakashdev.nexussphere.users.exception.UserNotFoundException;
import com.biswasakashdev.nexussphere.users.models.Users;
import com.biswasakashdev.nexussphere.users.repository.UsersRepository;
import com.biswasakashdev.nexussphere.users.services.AuthService;
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

                    return Mono.just(users);
                })
                .switchIfEmpty(Mono.error(() -> {
                    log.error("User not exists with credential: {}", credentials.emailOrUsername());
                    return new UserNotFoundException("User doesn't exist.");
                }));
    }

}
