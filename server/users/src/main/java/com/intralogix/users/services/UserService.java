package com.intralogix.users.services;

import com.intralogix.users.models.Users;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<Users> updateOrSaveUser(Users users);

    Mono<Users> findUserByEmailOrUsername(String emailOrUsername);

    Mono<Users> findUserById(String userId);

    Mono<Boolean> isUserExists(String userId);

    Mono<Boolean> isUserExistsWithUsername(String username);

}
