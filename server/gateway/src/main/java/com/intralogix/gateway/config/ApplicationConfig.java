package com.intralogix.gateway.config;


import com.intralogix.common.services.JwtService;
import com.intralogix.common.services.impl.JwtServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final Environment environment;



    @Bean
    JwtService jwtService(Environment environment){
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

    record RegisteredServiceURLs(String userService, String workspaceService) {
    }


    @Bean
    RegisteredServiceURLs registeredServiceURLs(){

        String usersURL = environment.getProperty("services.users");
        String workspaceURL = environment.getProperty("services.workspace");

        assert usersURL != null && workspaceURL != null;

        return new RegisteredServiceURLs(usersURL, workspaceURL);
    }

}
