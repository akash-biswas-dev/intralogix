package com.nexussphere.users.controller;


import com.nexussphere.common.auth.SessionCookies;
import com.nexussphere.users.exception.AccountNotEnabledException;
import com.nexussphere.common.auth.jwt.JwtService;
import com.nexussphere.common.response.ClientResponse;
import com.nexussphere.users.dtos.requests.NewUserRequest;
import com.nexussphere.users.dtos.requests.UserCredentials;
import com.nexussphere.users.dtos.requests.UserProfileRequest;
import com.nexussphere.users.dtos.response.Authorization;
import com.nexussphere.users.models.Users;
import com.nexussphere.users.services.AuthService;
import com.nexussphere.users.services.UserService;
import com.nexussphere.users.utils.UsersUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.security.auth.login.AccountLockedException;
import java.time.Duration;
import java.util.HashMap;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/auth")
public class AuthController {


    private final UserService userService;
    private final AuthService authService;
    private final JwtService jwtService;

    private static final int SESSION_AGE = 1;

    @PostMapping(value = "/register")
    public Mono<ResponseEntity<Void>> registerUser(@RequestBody NewUserRequest newUser) {
        return userService.createUser(newUser)
                .thenReturn(new ResponseEntity<>(HttpStatus.CREATED));
    }

    @PostMapping
    public Mono<ResponseEntity<ClientResponse<Authorization>>> login(
            @RequestBody UserCredentials credentials,
            @RequestParam(name = "rememberMe", required = false, defaultValue = "false") boolean rememberMe,
            ServerWebExchange exchange
    ) {

//        How many days the generated session token valid.
        int ageInDays = rememberMe ? 15 : SESSION_AGE;

        Mono<Users> usersMono = authService.validateUser(credentials);

        return usersMono
                .map(users -> {

                    String authorizationToken =
                            jwtService.generateAuthorization(users.getId(), new HashMap<>());
                    String session = jwtService.generateSession(users.getId(), Duration.ofDays(ageInDays));

                    Authorization authorization = new Authorization(
                            authorizationToken,
                            UsersUtils.getUserResponse(users)
                    );

                    ResponseCookie sessionCookie = generateCookie(session, ageInDays);

                    exchange.getResponse().addCookie(sessionCookie);
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(new ClientResponse<>(true, authorization, null));
                })
                .onErrorResume(AccountNotEnabledException.class, (err) -> {
                    String temporaryProfileUpdateSession = jwtService.generateSession(err.getUserId(), Duration.ofHours(1));
                    ResponseCookie profileUpdateCookie = ResponseCookie
                            .from(SessionCookies.COOKIE_SETUP_PROFILE.getCookieName())
                            .value(temporaryProfileUpdateSession)
                            .maxAge(Duration.ofHours(1))
                            .path(SessionCookies.COOKIE_SETUP_PROFILE.getPath())
                            .httpOnly(true)
                            .build();
                    exchange.getResponse().addCookie(profileUpdateCookie);

                    return Mono.just(ResponseEntity
                            .status(HttpStatus.TEMPORARY_REDIRECT)
                            .build());
                })
                .onErrorResume(AccountLockedException.class, (_err) ->
                        Mono.just(
                                ResponseEntity
                                        .status(HttpStatus.FORBIDDEN)
                                        .body(new ClientResponse<>(false, null, "")))
                )
                .onErrorResume(err -> Mono.just(
                        ResponseEntity
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(new ClientResponse<>(false, null, "Something went wrong."))
                ));
    }

    @PostMapping(value = "/generate-authorization")
    public Mono<ResponseEntity<?>> refreshAuthorization(
            @RequestHeader(name = "Authentication-Info") String userId
    ) {

        Mono<Users> usersMono = userService.findUserById(userId);
        return usersMono
                .map(users -> {
                    String token = jwtService
                            .generateAuthorization(users.getId(), new HashMap<>());

                    return ResponseEntity
                            .status(HttpStatus.CREATED)
                            .body(new Authorization(token,
                                    UsersUtils.getUserResponse(users)
                            ));
                });
    }


    @GetMapping(value = "/setup-profile")
    public Mono<ResponseEntity<?>> getProfileInfo(@RequestHeader(name = "Authentication-Info") String userId) {
        Mono<Boolean> isAccountEnabledMono = userService.isAccountEnabled(userId);
        return isAccountEnabledMono.map(isExists -> {
            if (!isExists) {
                return ResponseEntity
                        .ok()
                        .build();
            }

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        });
    }


    @PostMapping(value = "/setup-profile")
    public Mono<ResponseEntity<ClientResponse<Object>>> setupProfile(@RequestHeader(name = "Authentication-Info") String userId,
                                                                     @RequestBody UserProfileRequest userProfileRequest,
                                                                     ServerWebExchange exchange) {
        Mono<Users> usersMono = userService.updateUserProfile(userId, userProfileRequest);

        return usersMono
                .map(users -> {
                    ResponseCookie deleteProfileCookie = ResponseCookie
                            .from(SessionCookies.COOKIE_SETUP_PROFILE.getCookieName(), null)
                            .path(SessionCookies.COOKIE_SETUP_PROFILE.getPath())
                            .httpOnly(true)
                            .build();
                    exchange.getResponse().addCookie(deleteProfileCookie);

                    // After profile update add session details.
                    String session = jwtService.generateSession(users.getId(), Duration.ofDays(SESSION_AGE));
                    ResponseCookie sessionCookie = generateCookie(session, SESSION_AGE);
                    exchange.getResponse().addCookie(sessionCookie);
                    return ResponseEntity
                            .accepted()
                            .body(new ClientResponse<>(true, null, null));
                }).onErrorResume(Exception.class, (_err) ->
                        Mono.just(ResponseEntity
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(new ClientResponse<>(false, null, "Something went wrong."))
                        ));
    }

    private static ResponseCookie generateCookie(String session, int ageInDays) {
        return ResponseCookie
                .from(SessionCookies.COOKIE_SESSION.getCookieName())
                .value(session)
                .path(SessionCookies.COOKIE_SESSION.getPath())
                .httpOnly(true)
                .maxAge(Duration.ofDays(ageInDays))
                .build();
    }
}
