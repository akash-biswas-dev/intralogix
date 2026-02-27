package com.intralogix.users.controller;


import com.intralogix.users.dtos.requests.UserProfileRequest;
import com.intralogix.users.models.Users;
import com.intralogix.users.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;



}
