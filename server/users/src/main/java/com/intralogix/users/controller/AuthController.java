package com.intralogix.users.controller;


import com.intralogix.common.jwt.JwtService;
import com.intralogix.users.dtos.requests.NewUserRequest;
import com.intralogix.users.dtos.requests.UserCredentials;
import com.intralogix.users.dtos.response.Authorization;
import com.intralogix.users.exception.InvalidCredentialException;
import com.intralogix.users.exception.UserNotFoundException;
import com.intralogix.users.exception.UserProfileLockedException;
import com.intralogix.users.exception.UserProfileNotCompletedException;
import com.intralogix.users.models.Users;
import com.intralogix.users.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.HashMap;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping(value = "/register")
    public Mono<ResponseEntity<Void>> registerUser(@RequestBody NewUserRequest newUser) {

        Users user = Users.builder()
                .email(newUser.email())
                .password(passwordEncoder.encode(newUser.password()))
                .joinedOn(LocalDate.now())
                .isAccountEnabled(true)
                .isAccountLocked(false)
                .build();
        return userService.createUser(user)
                .thenReturn(new ResponseEntity<>(HttpStatus.CREATED));
    }

    @PostMapping
    public Mono<ResponseEntity<?>> login(
            @RequestBody UserCredentials credentials,
            @RequestParam(name = "rememberMe", required = false, defaultValue = "false") boolean rememberMe
    ) {

        int age = rememberMe ? 15 * 24 * 60 : 60 *24;

        Mono<Users> usersMono = userService.findUserByEmailOrUsername(
                credentials.emailOrUsername()
        );

        return usersMono
                .map(users -> {
                    if (!users.getIsAccountEnabled()) {
                        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                    }

                    if (users.getIsAccountLocked()) {
                        return ResponseEntity
                                .status(HttpStatus.FORBIDDEN)
                                .body("Profile locked contact to administrator.");
                    }

                    boolean passwordMatches = passwordEncoder.matches(
                            credentials.password(),
                            users.getPassword()
                    );
                    if (!passwordMatches) {
                        return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .body("Invalid username and password");
                    }

                    String authorizationToken =
                            jwtService.generateAuthorization(users.getId(), new HashMap<>());
                    String session = jwtService.generateSession(users.getId(), age);

                    Authorization authorization = new Authorization(
                            authorizationToken,
                            UserService.getUserResponse(users)
                    );

                    ResponseCookie cookie = ResponseCookie
                            .from("SESSION")
                            .value(session)
                            .path("/api/v1/auth/refresh-authorization")
                            .httpOnly(true)
                            .maxAge(age)
                            .build();
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .header(HttpHeaders.SET_COOKIE, cookie.toString())
                            .body(authorization);
                }).onErrorResume(err -> Mono.just(
                        ResponseEntity
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(err.getMessage())
                ));
    }

    @PostMapping(value = "/refresh-authorization")
    public Mono<ResponseEntity<?>> refreshAuthorization(
            @RequestHeader(name = "Authentication-Info") String userId
    ){

        Mono<Users> usersMono = userService.findUserById(userId);
        return usersMono
                .map(users->{
                    String token = jwtService
                            .generateAuthorization(users.getId(), new HashMap<>());

                    return ResponseEntity
                            .status(HttpStatus.CREATED)
                            .body(new Authorization(token, null));
                });
    }
}
