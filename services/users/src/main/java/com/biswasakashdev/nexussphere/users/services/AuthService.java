package com.biswasakashdev.nexussphere.users.services;

import com.biswasakashdev.nexussphere.users.dtos.requests.UserCredentials;
import com.biswasakashdev.nexussphere.users.models.Users;
import reactor.core.publisher.Mono;

public interface AuthService {
    Mono<Users> validateUser(UserCredentials userCredentials);
}
