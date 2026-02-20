package com.intralogix.users.services;

import reactor.core.publisher.Mono;

public interface UserService {

    Mono<Void> createUser(String email, String password);


}
