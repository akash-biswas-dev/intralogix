package com.biswasakashdev.nexussphere.common.auth.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    private JwtService jwtService;


    @BeforeEach
    void beforeEach(){
        this.jwtService = new JwtServiceImpl(
                "1A50EF4F7AB1F6069BC04EEABFA52D66257DHFAJKSHFJKHFJKA",
                "http://localhost:8080"
        );
    }

    @Test
    void shouldThrowExpiredJwtExceptionWhenPassAExpiredToken() throws InterruptedException {
        String userId= "user-id";
        String token = jwtService.generateSession(userId, Duration.ofMillis(100L));
        Thread.sleep(100L);

        assertThrows(ExpiredJwtException.class,()->{
           jwtService.getUserId(token);
        });

    }

    @Test
    void shouldThrowsMalformedJwtExceptionWhenPassAnInvalidToken() throws InterruptedException {
       String token = "invalid-token";
       assertThrows(MalformedJwtException.class,()->{
           jwtService.getUserId(token);
       });
    }
}