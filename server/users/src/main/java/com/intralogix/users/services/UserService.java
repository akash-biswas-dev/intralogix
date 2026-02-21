package com.intralogix.users.services;

import com.intralogix.common.response.UserResponse;
import com.intralogix.users.models.UserProfile;
import com.intralogix.users.models.Users;
import reactor.core.publisher.Mono;

public interface UserService {

    static UserResponse getUserResponse(Users users) {
        UserProfile profile = users.getUserProfile();
        return new UserResponse(
                users.getUsername(),
                profile.getFirstName(),
                profile.getLastName()
        );
    }

    Mono<Void> createUser(Users users);

    Mono<Users> findUserByEmailOrUsername(String emailOrUsername);

    Mono<Users> findUserById(String userId);
}
