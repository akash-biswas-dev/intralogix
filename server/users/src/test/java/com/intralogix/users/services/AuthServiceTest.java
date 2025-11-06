package com.intralogix.users.services;

import com.intralogix.common.services.JwtService;
import com.intralogix.users.dtos.requests.UserCredentials;
import com.intralogix.users.dtos.response.AuthTokens;
import com.intralogix.users.models.Role;
import com.intralogix.users.models.Users;
import com.intralogix.users.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest
class AuthServiceTest {

    @MockitoBean
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService;

    private Users savedUser;

    @BeforeEach
    void beforeEachTest() {
        this.savedUser = Users.builder()
                .id("mongo-id")
                .email("test@mail.com")
                .username("test")
                .password(passwordEncoder.encode("test"))
                .isAccountNonLocked(true)
                .isAccountNonExpired(true)
                .isAccountEnabled(false)
                .dateOfBirth(LocalDate.parse("1999-11-27"))
                .role(Role.USER)
                .joinedOn(LocalDate.now())
                .build();
    }

    @Test
    void generateAuthTokensWhenUserUsernameAndPasswordIsCorrect(){
        UserCredentials userCredentials = new UserCredentials("test","test");
        when(usersRepository.findByEmailOrUsernameIgnoreCase(any(String.class),any(String.class))).thenReturn(Optional.of(this.savedUser));


        AuthTokens authTokens = this.authService.login(userCredentials);

        UserDetails authorizedUser = jwtService.extractUserDetails(authTokens.accessToken());
        UserDetails refreshTokenUser = jwtService.extractUserDetails(authTokens.refreshToken());

        assertEquals(authorizedUser.getUsername(), this.savedUser.getUsername());
        authorizedUser.getAuthorities().forEach(authority ->
                assertTrue(this.savedUser.getAuthorities().contains(new SimpleGrantedAuthority(authority.getAuthority()))));


        assertEquals(refreshTokenUser.getUsername(), this.savedUser.getUsername());
        refreshTokenUser.getAuthorities().forEach(authority ->
                assertTrue(this.savedUser.getAuthorities().contains(new SimpleGrantedAuthority(authority.getAuthority()))));
    }
}