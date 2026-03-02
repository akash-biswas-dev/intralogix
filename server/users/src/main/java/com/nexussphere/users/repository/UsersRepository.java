package com.nexussphere.users.repository;

import com.nexussphere.users.models.Users;
import reactor.core.publisher.Mono;


public interface UsersRepository {

    Mono<Users> saveUser(Users users);

    Mono<Users> findByEmailOrUsername(String emailOrUsername);

    Mono<Users> findById(String userId);

    Mono<Boolean> isUserExists(String userId);
}
