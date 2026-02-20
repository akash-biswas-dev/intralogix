package com.intralogix.users.controller;


import com.intralogix.users.dtos.requests.NewUserRequest;
import com.intralogix.users.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping(value = "/register")
    public Mono<ResponseEntity<Void>> registerUser(NewUserRequest newUser){
        return userService.createUser(newUser.email(), newUser.password())
                .thenReturn(new ResponseEntity<>(HttpStatus.CREATED));
    }
}
