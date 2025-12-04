package com.intralogix.users.exception.hadlere;

import com.intralogix.common.dtos.AccessToken;
import com.intralogix.common.dtos.AuthToken;
import com.intralogix.common.exceptions.AccountNotEnabledException;
import com.intralogix.common.services.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;


@RequiredArgsConstructor
@RestControllerAdvice
public class AuthExceptionHandler {

    private final JwtService jwtService;


    @ExceptionHandler(AccountNotEnabledException.class)
    public ResponseEntity<Map<String,String>> accountNotEnabledExceptionHandle(AccountNotEnabledException ex, HttpServletResponse response) {
        String userId = ex.getMessage();
        String token = jwtService.generateToken(userId,15*60);
        return new ResponseEntity<>(Map.of("token",token), HttpStatus.TEMPORARY_REDIRECT);
    }

}
