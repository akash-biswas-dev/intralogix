package com.biswasakashdev.nexussphere.users.services.impl;

import com.biswasakashdev.nexussphere.users.dtos.requests.NewUserRequest;
import com.biswasakashdev.nexussphere.users.dtos.requests.UserProfileRequest;
import com.biswasakashdev.nexussphere.users.exception.ProfileNotCompleteException;
import com.biswasakashdev.nexussphere.users.exception.UserAlreadyExistsException;
import com.biswasakashdev.nexussphere.users.exception.UserNotFoundException;
import com.biswasakashdev.nexussphere.users.models.UserProfile;
import com.biswasakashdev.nexussphere.users.models.Users;
import com.biswasakashdev.nexussphere.users.repository.UsersRepository;
import com.biswasakashdev.nexussphere.users.repository.impl.UserRepositoryImpl;
import com.biswasakashdev.nexussphere.users.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.security.auth.login.AccountLockedException;
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
                .userProfile(
                        UserProfile.builder()
                                .firstName(newUser.firstName())
                                .lastName(newUser.lastName())
                                .build()
                )
                .joinedOn(Instant.now())
                .profileCompleted(false)
                .accountLocked(false)
                .build();
        return usersRepository
                .saveUser(user)
                .onErrorResume(
                        DuplicateKeyException.class,
                        (err)-> {
                            log.error("Try create a user with an existing email: {}",newUser.email());
                            return Mono.error(
                                    new UserAlreadyExistsException(
                                            newUser.email(),
                                            "User already exists"
                                    ));
                        }
                );
    }

    @Override
    public Mono<Users> updateOrSaveUser(Users users) {
        return usersRepository.saveUser(users);
    }

    @Override
    public Mono<Users> findUserByEmailOrUsername(String emailOrUsername) {
        Mono<Users> usersMono = usersRepository
                .findByEmailOrUsername(emailOrUsername)
                .switchIfEmpty(Mono.create(sink -> {
                    log.error("User not found with Email or Username: {}", emailOrUsername);
                    sink.error(new UserNotFoundException("User not found"));
                }));

        return verifyUsers(usersMono);
    }

    @Override
    public Mono<Users> findUserById(String userId) {
        Mono<Users> usersMono = usersRepository
                .findById(userId)
                .switchIfEmpty(Mono.create(sink -> {
                    log.error("User not found with id: {}", userId);
                    sink.error(new UserNotFoundException("User not found"));
                }));
        return verifyUsers(usersMono);
    }

    protected Mono<Users> verifyUsers(Mono<Users> usersMono) {
        return usersMono
                .flatMap(users -> {
                    if (users.getAccountLocked()) {
                        log.warn("Account locked for user: {}", users.getId());
                        return Mono.error(new AccountLockedException("Account locked, contact to administrator."));
                    }

                    return Mono.just(users);
                });
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
                                    .isProfileCompleted(true)
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
        return userMono.map(Users::getProfileCompleted);
    }

}
