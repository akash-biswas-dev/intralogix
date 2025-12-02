package com.intralogix.users.services;

import com.intralogix.common.services.JwtService;
import com.intralogix.users.dtos.requests.UserCredentials;
import com.intralogix.users.dtos.response.AuthTokens;
import com.intralogix.users.models.Role;
import com.intralogix.users.models.Users;
import com.intralogix.users.services.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private JwtService jwtService;

    private Users savedUser;

    @BeforeEach
    void beforeEachTest() {
        this.savedUser = Users.builder()
                .id("mongo-id")
                .email("test@mail.com")
                .username("test")
                .password("test")
                .isAccountNonLocked(true)
                .isAccountNonExpired(true)
                .isAccountEnabled(false)
                .role(Role.USER)
                .joinedOn(LocalDate.now())
                .build();
    }

    @Test
    void generateAuthTokensWhenUserUsernameAndPasswordIsCorrect() {

        String testToken = "testToken";
        UserCredentials userCredentials = new UserCredentials("test", "test");

        when(userService.findUserByEmailOrUsername(userCredentials.usernameOrEmail()))
                .thenReturn(this.savedUser);
        when(passwordEncoder.matches(userCredentials.password(), savedUser.getPassword())).thenReturn(Boolean.TRUE);

        when(jwtService.generateToken(this.savedUser)).thenReturn(testToken);
       when(jwtService.generateRefreshToken(this.savedUser.getId())).thenReturn(testToken);

        AuthTokens authTokensWithRemember = this.authService.login(userCredentials,true);

        assertEquals(testToken, authTokensWithRemember.accessToken());
        assertEquals(testToken, authTokensWithRemember.refreshToken());
        assertEquals(this.savedUser.isEnabled(),authTokensWithRemember.isAccountEnabled());

        AuthTokens authTokensWithoutRemember = this.authService.login(userCredentials,false);

        assertEquals(testToken, authTokensWithoutRemember.accessToken());
        assertNull(authTokensWithoutRemember.refreshToken());
    }
}