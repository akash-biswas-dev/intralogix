package com.intralogix.users.config;


import com.intralogix.common.services.JwtService;
import com.intralogix.common.services.impl.JwtServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@RequiredArgsConstructor
public class JwtConfig {
    private final Environment environment;

    @Bean
    JwtService jwtService (){
        String secret = environment.getProperty("jwt.secret");
        String issuer = environment.getProperty("jwt.issuer");
        Long expiration = environment.getProperty("jwt.expiration", Long.class);
        Long refreshExpiration = environment.getProperty("jwt.refresh-expiration", Long.class);

        if(secret == null ||  expiration == null || refreshExpiration == null ||  issuer == null){
            throw new IllegalStateException("Jwt properties cant be null");
        }

        return new JwtServiceImpl(
                secret,
                expiration,
                refreshExpiration,
                issuer
        );
    }
}
