package com.biswasakashdev.nexussphere.users.controller;

import com.biswasakashdev.nexussphere.common.auth.jwt.JwtService;
import com.biswasakashdev.nexussphere.users.dtos.requests.NewUserRequest;
import com.biswasakashdev.nexussphere.users.dtos.requests.UserCredentials;
import com.biswasakashdev.nexussphere.users.exception.InvalidCredentialException;
import com.biswasakashdev.nexussphere.users.exception.ProfileNotCompleteException;
import com.biswasakashdev.nexussphere.users.exception.UserAlreadyExistsException;
import com.biswasakashdev.nexussphere.users.exception.UserNotFoundException;
import com.biswasakashdev.nexussphere.users.services.AuthService;
import com.biswasakashdev.nexussphere.users.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webflux.test.autoconfigure.WebFluxTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


@WebFluxTest(AuthController.class)
class AuthControllerTest {


    @Autowired
    private WebTestClient webClient;


    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private JwtService jwtService;

    @Test
    void shouldReturn400IfTryCreateAUserWithAnExistingEmail() {
        NewUserRequest request = new NewUserRequest(
                "abc@email.com",
                "password",
                "John",
                "Doe"
        );

        when(userService.createUser(request))
                .thenReturn(Mono.error(new UserAlreadyExistsException(request.email(),
                        "User already exists.")));

        webClient
                .post()
                .uri("/api/v1/auth/register")
                .bodyValue(request)
                .exchange()
                .expectStatus()
                .isBadRequest();

    }

    @Test
    void shouldReturn404WhenSendAnInvalidUsername() {

        UserCredentials credentials = new UserCredentials(
                "abc@email.com",
                "password"
        );

        when(authService.validateUser(credentials))
                .thenReturn(Mono.error(new UserNotFoundException("User dont exist.")));

        webClient.post()
                .uri("/api/v1/auth")
                .bodyValue(credentials)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void shouldReturn400WhenSendAnInvalidPassword() {
        UserCredentials credentials = new UserCredentials(
                "abc@email.com",
                "password"
        );

        when(authService.validateUser(credentials))
                .thenReturn(Mono.error(new InvalidCredentialException("Invalid credentials.")));

        webClient.post()
                .uri("/api/v1/auth")
                .bodyValue(credentials)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    void shouldReturn302WhenLoginWithAnUncompletedProfile() {
        UserCredentials credentials = new UserCredentials(
                "abc@email.com",
                "password"
        );

        String userId = "a-long-userId";

        when(authService.validateUser(credentials))
                .thenReturn(Mono.error(new ProfileNotCompleteException(userId)));

        when(jwtService.buildToken(eq(userId),any(Duration.class),any())).thenReturn("token");


        webClient.post()
                .uri("/api/v1/auth")
                .bodyValue(credentials)
                .exchange()
                .expectStatus()
                .isTemporaryRedirect()
                .expectHeader()
                .valueEquals("Location", "/setup-profile")
                .expectBody()
                .jsonPath("$.token")
                .exists()
                .jsonPath("$.maxAge")
                .exists()
                .jsonPath("$.user")
                .doesNotExist();

    }

}