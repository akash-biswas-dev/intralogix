package com.biswasakashdev.nexussphere.users.controller;


import com.biswasakashdev.nexussphere.common.auth.AccountStatus;
import com.biswasakashdev.nexussphere.common.auth.jwt.JwtService;
import com.biswasakashdev.nexussphere.common.response.ClientResponse;
import com.biswasakashdev.nexussphere.users.dtos.requests.NewUserRequest;
import com.biswasakashdev.nexussphere.users.dtos.requests.UserCredentials;
import com.biswasakashdev.nexussphere.users.dtos.requests.UserProfileRequest;
import com.biswasakashdev.nexussphere.users.dtos.response.Authorization;
import com.biswasakashdev.nexussphere.users.exception.InvalidCredentialException;
import com.biswasakashdev.nexussphere.users.exception.UserNotFoundException;
import com.biswasakashdev.nexussphere.users.models.Users;
import com.biswasakashdev.nexussphere.users.services.AuthService;
import com.biswasakashdev.nexussphere.users.services.UserService;
import com.biswasakashdev.nexussphere.users.utils.UsersUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.security.auth.login.AccountLockedException;
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
    public Mono<ResponseEntity<Void>> registerUser(
            @RequestBody NewUserRequest newUser
    ) {
        return userService.createUser(newUser)
                .thenReturn(new ResponseEntity<>(HttpStatus.CREATED));
    }

    @PostMapping
    public Mono<ResponseEntity<ClientResponse<Authorization>>> login(
            @RequestBody UserCredentials credentials,
            @RequestParam(name = "rememberMe", required = false, defaultValue = "false") boolean rememberMe
    ) {

//        How many days the generated session token valid.
        Duration duration = rememberMe ? Duration.ofDays(15) : Duration.ofDays(1);

        Mono<Users> usersMono = authService.validateUser(credentials);

        return usersMono
                .map(users -> {

                    String token = jwtService.buildToken(
                            users.getId(),
                            Duration.ofHours(1),
                            new HashMap<>()
                    );
                    Authorization authorization = new Authorization(
                            token,
                            duration.toSeconds(),
                            UsersUtils.getUserResponse(users)
                    );

                    return ResponseEntity
                            .status(HttpStatus.CREATED)
                            .body(new ClientResponse<>(true, authorization, null));
                });
    }

//    After updating the profile user generate new Authorization.

    @PostMapping("/refresh-authorization")
    public Mono<ResponseEntity<ClientResponse<Authorization>>> refreshAuthorization(
            @RequestHeader("Authentication-Info") String userId
    ) {
        Mono<Users> userMono = userService.findUserById(userId);

        return userMono
                .map(user -> {
                    if (!user.getIsProfileCompleted()) {
                        ClientResponse<Authorization> userResp = new ClientResponse<>(
                                false,
                                null,
                                "User profile not completed"
                        );
                        return ResponseEntity
                                .status(HttpStatus.FORBIDDEN)
                                .body(userResp);
                    }

                    Duration expiration = Duration.ofDays(1);

                    String token = jwtService.buildToken(
                            user.getId(),
                            expiration,
                            new HashMap<>()
                    );

                    Authorization authorization = new Authorization(
                            token,
                            expiration.toSeconds(),
                            UsersUtils.getUserResponse(user)
                    );

                    return ResponseEntity
                            .status(HttpStatus.CREATED)
                            .body(new ClientResponse<>(
                                    true,
                                    authorization,
                                    null
                            ));
                });
    }
}
