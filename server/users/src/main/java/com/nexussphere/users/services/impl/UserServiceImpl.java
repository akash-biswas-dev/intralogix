package com.nexussphere.users.services.impl;

import com.nexussphere.users.dtos.requests.NewUserRequest;
import com.nexussphere.users.dtos.requests.UserProfileRequest;
import com.nexussphere.users.models.Users;
import com.nexussphere.users.repository.UsersRepository;
import com.nexussphere.users.repository.impl.UserRepositoryImpl;
import com.nexussphere.users.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<Users> createUser(NewUserRequest newUser) {
        Users user = Users.builder()
                .email(newUser.email())
                .password(passwordEncoder.encode(newUser.password()))
                .joinedOn(Instant.now())
                .accountEnabled(false)
                .accountLocked(false)
                .build();
        return usersRepository.saveUser(user);
    }

    @Override
    public Mono<Users> updateOrSaveUser(Users users) {
        return usersRepository.saveUser(users);
    }

    @Override
    public Mono<Users> findUserByEmailOrUsername(String emailOrUsername) {
        return usersRepository
                .findByEmailOrUsername(emailOrUsername)
                .onErrorResume((err) -> {
                    log.error("Error occurred while fetch user with username: {} , message: {}", emailOrUsername, err.getMessage());
                    return Mono.error(new RuntimeException("Something went wrong, try again after some time."));
                });
    }

    @Override
    public Mono<Users> findUserById(String userId) {
        return usersRepository
                .findById(userId);
    }

    @Override
    public Mono<Boolean> isUserExists(String userId) {
        return usersRepository.isUserExists(userId);
    }

    @Override
    public Mono<Users> updateUserProfile(String userId, UserProfileRequest profileRequest) {

        return usersRepository
                .findById(userId)
                .flatMap((users) -> {
                    // Update username and account status.
                    Mono<Users> updateResultUserMono = usersRepository.updateUser(
                            userId,
                            UserRepositoryImpl.UserUpdates.builder()
                                    .username(profileRequest.username())
                                    .isAccountEnabled(true)
                                    .build(),
                            true
                    );

                    //Add user profile.

                    Mono<Users> userProfileUpdateMono = usersRepository.updateProfile(
                            userId,
                            UsersRepository.UserProfileUpdates.builder()
                                    .firstName(profileRequest.firstName())
                                    .lastName(profileRequest.lastName())
                                    .gender(profileRequest.gender())
                                    .dateOfBirth(LocalDate.parse(profileRequest.dateOfBirth()))
                                    .build(),
                            true
                    );

                    return updateResultUserMono.then(userProfileUpdateMono);
                })
                .switchIfEmpty(Mono.error(new RuntimeException()))
                .onErrorResume(Throwable.class, (err) -> {
                    log.error("User profile setup not successful for user: {}", userId);
                    return Mono.error(new RuntimeException("Service unavailable please try agiain later."));
                });
    }

    @Override
    public Mono<Boolean> isUserExistsWithUsername(String username) {
        return usersRepository.isUserExists(username);
    }

    @Override
    public Mono<Boolean> isAccountEnabled(String userId) {
        Mono<Users> userMono = usersRepository.findById(userId);
        return userMono.map(Users::getAccountEnabled);
    }

}
