package com.biswasakashdev.nexussphere.users.controller;


import com.biswasakashdev.nexussphere.common.auth.AccountStatus;
import com.biswasakashdev.nexussphere.common.auth.jwt.JwtService;
import com.biswasakashdev.nexussphere.users.dtos.requests.NewUserRequest;
import com.biswasakashdev.nexussphere.users.dtos.requests.UserCredentials;
import com.biswasakashdev.nexussphere.users.dtos.response.Authorization;
import com.biswasakashdev.nexussphere.users.exception.ProfileNotCompleteException;
import com.biswasakashdev.nexussphere.users.models.Users;
import com.biswasakashdev.nexussphere.users.services.AuthService;
import com.biswasakashdev.nexussphere.users.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/auth")
public class AuthController {


    private final UserService userService;
    private final AuthService authService;
    private final JwtService jwtService;

    private static final int SESSION_AGE = 1;

    @PostMapping(value = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> registerUser(
            @RequestBody NewUserRequest newUser
    ) {
        return userService
                .createUser(newUser)
                .then();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Authorization> login(
            @RequestBody UserCredentials credentials,
            @RequestParam(name = "rememberMe", required = false, defaultValue = "false") boolean rememberMe
    ) {

//        How many days the generated session token valid.
        Duration duration = rememberMe ? Duration.ofDays(15) : Duration.ofDays(1);

        Mono<Users> usersMono = authService.validateUser(credentials);

        return usersMono
                .flatMap(users -> {

                    if (!users.getProfileCompleted()) {
                        log.error("User profile not completed with userId : {}", users.getId());
                        return Mono.error(new ProfileNotCompleteException(users.getId()));
                    }

                    String token = jwtService.buildToken(
                            users.getId(),
                            Duration.ofHours(1),
                            Map.of("account_status", AccountStatus.ACTIVE)
                    );


                    Authorization authorization = new Authorization(
                            token,
                            duration.toSeconds()
                    );


                    return Mono.just(authorization);
                });
    }

//    After updating the profile user generate new Authorization.

    @GetMapping("/refresh-authorization")
    public Mono<ResponseEntity<Authorization>> refreshAuthorization(
            @RequestHeader("Authentication-Info") String userId
    ) {
        Mono<Users> userMono = userService.findUserById(userId);

        return userMono
                .map(user -> {
                    if (!user.getProfileCompleted()) {

                        return ResponseEntity
                                .status(HttpStatus.FORBIDDEN)
                                .build();
                    }

                    Duration expiration = Duration.ofDays(1);

                    String token = jwtService.buildToken(
                            user.getId(),
                            expiration,
                            new HashMap<>()
                    );

                    Authorization authorization = new Authorization(
                            token,
                            expiration.toSeconds()
                    );

                    return ResponseEntity
                            .status(HttpStatus.OK)
                            .body(authorization);
                });
    }
}
