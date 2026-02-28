package com.intralogix.users.services;

import com.intralogix.users.dtos.requests.NewUserRequest;
import com.intralogix.users.dtos.requests.UserProfileRequest;
import com.intralogix.users.models.Users;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<Users> createUser(NewUserRequest newUserRequest);

    Mono<Users> updateOrSaveUser(Users users);

    Mono<Users> findUserByEmailOrUsername(String emailOrUsername);

    Mono<Users> findUserById(String userId);

    Mono<Boolean> isUserExists(String userId);

    Mono<Users> updateUserProfile(String userId,UserProfileRequest profileRequest);

    Mono<Boolean> isUserExistsWithUsername(String username);

    Mono<Boolean> isAccountEnabled(String userId);
}
