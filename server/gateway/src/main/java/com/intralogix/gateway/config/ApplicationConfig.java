package com.intralogix.gateway.config;


import com.intralogix.common.jwt.JwtService;
import com.intralogix.common.jwt.JwtServiceImpl;
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

        if(secret == null  ||  issuer == null){
            throw new IllegalStateException("Jwt properties cant be null");
        }
        return new JwtServiceImpl(
                secret,
                issuer
        );
    }

    public record RegisteredServiceURLs(String userService, String workspaceService) {
    }


    @Bean
    RegisteredServiceURLs registeredServiceURLs(){

        String usersURL = environment.getProperty("services.users");
        String workspaceURL = environment.getProperty("services.workspace");

        assert usersURL != null && workspaceURL != null;

        return new RegisteredServiceURLs(usersURL, workspaceURL);
    }

}
