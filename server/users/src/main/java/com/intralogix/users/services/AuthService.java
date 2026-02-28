package com.intralogix.users.services;

import com.intralogix.users.dtos.requests.UserCredentials;
import com.intralogix.users.models.Users;
import reactor.core.publisher.Mono;

public interface AuthService {
    Mono<Users> validateUser(UserCredentials userCredentials);
}
