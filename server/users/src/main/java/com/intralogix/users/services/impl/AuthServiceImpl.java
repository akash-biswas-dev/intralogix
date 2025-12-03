package com.intralogix.users.services.impl;

import com.intralogix.common.dtos.AccessToken;
import com.intralogix.common.dtos.AuthToken;

import com.intralogix.common.services.JwtService;
import com.intralogix.users.dtos.requests.UserCredentials;
import com.intralogix.users.exception.UserNotFoundException;
import com.intralogix.users.models.Users;
import com.intralogix.users.services.AuthService;
import com.intralogix.users.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthToken login(UserCredentials userCredentials, Boolean rememberMe) {
        final Users users;
        try {
            users = userService.findUserByEmailOrUsername(userCredentials.usernameOrEmail());
        } catch (NoSuchElementException e) {
            String msg = String.format("User not found with username %s", userCredentials.usernameOrEmail());
            log.error(msg);
            throw new UserNotFoundException(msg);
        }

        if (!passwordEncoder.matches(userCredentials.password(), users.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }
        return jwtService.generateToken(users.getId(), rememberMe);
    }

    @Override
    public AccessToken refreshAccessToken(String userId) {
        final Users users;
        try{
            users = userService.findUserByEmailOrUsername(userId);
        }catch (NoSuchElementException e){
            log.error("User not found with user id {}", userId);
            throw new UserNotFoundException("User not found");
        }
        return jwtService.generateAccessToken(users, new HashMap<>());
    }
}
