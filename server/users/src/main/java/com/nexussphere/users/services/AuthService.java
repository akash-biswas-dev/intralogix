package com.nexussphere.users.services;

import com.nexussphere.users.dtos.requests.UserCredentials;
import com.nexussphere.users.models.Users;
import reactor.core.publisher.Mono;

public interface AuthService {
    Mono<Users> validateUser(UserCredentials userCredentials);
}
