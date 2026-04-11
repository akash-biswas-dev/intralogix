package com.biswasakashdev.nexussphere.gateway.config;


import com.biswasakashdev.nexussphere.common.auth.SessionCookies;
import com.biswasakashdev.nexussphere.common.auth.jwt.JwtService;
import com.biswasakashdev.nexussphere.gateway.filters.CookieAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CookieFilterConfig {


    private final JwtService jwtService;

    @Bean
    CookieAuthorizationFilter generateAuthorizationFilter() {
        return new CookieAuthorizationFilter(jwtService,SessionCookies.COOKIE_SESSION);
    }

    @Bean
    CookieAuthorizationFilter profileUpdateTokenFilter() {
        return new CookieAuthorizationFilter(jwtService, SessionCookies.COOKIE_SETUP_PROFILE);
    }
}
