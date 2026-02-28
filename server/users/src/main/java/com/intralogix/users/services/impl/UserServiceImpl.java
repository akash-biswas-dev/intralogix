package com.intralogix.users.services.impl;

import com.intralogix.users.dtos.requests.NewUserRequest;
import com.intralogix.users.dtos.requests.UserProfileRequest;
import com.intralogix.users.exception.UserNotFoundException;
import com.intralogix.users.models.UserProfile;
import com.intralogix.users.models.Users;
import com.intralogix.users.repository.UsersRepository;
import com.intralogix.users.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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
                .joinedOn(LocalDate.now())
                .isAccountEnabled(false)
                .isAccountLocked(false)
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

                    UserProfile userProfile = UserProfile.builder()
                            .firstName(profileRequest.firstName())
                            .lastName(profileRequest.lastName())
                            .dateOfBirth(LocalDate.parse(profileRequest.dateOfBirth()))
                            .gender(profileRequest.gender())
                            .build();

                    users.setUserProfile(userProfile);
                    users.setIsAccountEnabled(true);

                    return usersRepository.saveUser(users);
                });
    }

    @Override
    public Mono<Boolean> isUserExistsWithUsername(String username) {
        return usersRepository.isUserExists(username);
    }

    @Override
    public Mono<Boolean> isAccountEnabled(String userId) {
        Mono<Users> userMono = usersRepository.findById(userId);
        return userMono.map(Users::getIsAccountEnabled);
    }

}
