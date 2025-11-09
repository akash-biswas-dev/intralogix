package com.intralogix.users.controller;


import com.intralogix.users.dtos.requests.NewUserRequest;
import com.intralogix.users.dtos.requests.UserCredentials;
import com.intralogix.users.dtos.response.AuthTokens;
import com.intralogix.common.response.UserResponse;
import com.intralogix.users.services.AuthService;
import com.intralogix.users.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping(value = "/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody NewUserRequest newUser) {
        return new ResponseEntity<>(userService.saveUser(newUser), HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<AuthTokens> loginUser(@RequestBody UserCredentials userCredentials, @RequestParam(name = "rememberMe", required = false,defaultValue = "false") Boolean rememberMe){
        AuthTokens authTokens = authService.login(userCredentials);
        return ResponseEntity.ok(authTokens);
    }

    

}
