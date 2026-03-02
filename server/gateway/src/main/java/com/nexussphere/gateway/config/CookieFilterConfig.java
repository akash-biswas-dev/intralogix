package com.nexussphere.gateway.config;


import com.nexussphere.common.auth.SessionCookies;
import com.nexussphere.common.auth.jwt.JwtService;
import com.nexussphere.gateway.filters.CookieAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CookieFilterConfig {


    private final JwtService jwtService;

    @Bean
    CookieAuthorizationFilter refreshTokenFilter() {
        return new CookieAuthorizationFilter(jwtService,SessionCookies.COOKIE_SESSION);
    }

    @Bean
    CookieAuthorizationFilter profileUpdateTokenFilter() {
        return new CookieAuthorizationFilter(jwtService, SessionCookies.COOKIE_SETUP_PROFILE);
    }
}
