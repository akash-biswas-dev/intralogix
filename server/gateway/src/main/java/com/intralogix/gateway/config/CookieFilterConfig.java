package com.intralogix.gateway.config;


import com.intralogix.common.jwt.JwtService;
import com.intralogix.gateway.filters.CookieAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CookieFilterConfig {

    private final JwtService jwtService;

    @Bean
    CookieAuthorizationFilter refreshTokenFilter() {
        return new CookieAuthorizationFilter(jwtService,"SESSION");
    }


}
