package com.biswasakashdev.nexussphere.users.repository;

import com.biswasakashdev.nexussphere.users.config.AuthConfig;
import com.biswasakashdev.nexussphere.users.models.Users;
import com.biswasakashdev.nexussphere.users.repository.impl.UserRepositoryImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.mongodb.test.autoconfigure.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;


@DataMongoTest
@ActiveProfiles("test")
@Import(value = {
        UserRepositoryImpl.class,
        AuthConfig.class,
})
class UsersRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ReactiveMongoTemplate mongoTemplate;

    @AfterEach
    void clean() {
        mongoTemplate.dropCollection(Users.class).block();
    }


    @Test
    void shouldSaveUser() {
        String userPassword = "password";
        String email = "abc@gmail.com";
        Users user = Users.builder()
                .email(email)
                .password(passwordEncoder.encode(userPassword))
                .joinedOn(Instant.now())
                .joinedOn(Instant.now())
                .build();
        usersRepository.saveUser(user)
            .then(usersRepository.findByEmailOrUsername(email))
            .as(StepVerifier::create)
            .consumeNextWith(savedUser -> {
                assertEquals(email, savedUser.getEmail());
                assertNotNull(savedUser.getJoinedOn());
            })
            .verifyComplete();
    }

    @Test
    void shouldReturnFalseIfUserProfileNotCompleted() {
        String userPassword = "password";
        String email = "abc@gmail.com";
        Users user = Users.builder()
                .email(email)
                .password(passwordEncoder.encode(userPassword))
                .joinedOn(Instant.now())
                .isProfileCompleted(false)
                .accountLocked(false)
                .build();
        Mono<Users> usersMono = usersRepository.saveUser(user);
        usersMono.flatMap(users -> usersRepository.isProfileCompleted(users.getId()))
                .as(StepVerifier::create)
                .expectNext(false)
                .expectComplete()
                .verify();

    }



}