package com.intralogix.users.controller;


import com.intralogix.common.dtos.AuthToken;
import com.intralogix.common.response.UserResponse;
import com.intralogix.users.dtos.requests.NewUserRequest;
import com.intralogix.users.dtos.requests.UserCredentials;
import com.intralogix.users.services.AuthService;
import com.intralogix.users.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping(value = "/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody NewUserRequest newUser) {
        return new ResponseEntity<>(userService.saveUser(newUser), HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<AuthToken> loginUser(
            @RequestBody UserCredentials userCredentials,
            @RequestParam(name = "rememberMe", required = false, defaultValue = "false") Boolean rememberMe) {
        AuthToken authTokens = authService.login(userCredentials, rememberMe);
        return new ResponseEntity<>(authTokens, HttpStatus.CREATED);
    }

    @PostMapping(value = "/refresh-token")
    public ResponseEntity<Map<String,String>> refreshToken(@RequestHeader(name = "X-User-Id") String userId){
        String accessToken = authService.refreshAccessToken(userId);
        return new ResponseEntity<>(Map.of("token",accessToken), HttpStatus.CREATED);
    }

}
