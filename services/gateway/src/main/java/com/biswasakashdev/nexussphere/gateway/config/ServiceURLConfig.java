package com.biswasakashdev.nexussphere.gateway.config;


import com.biswasakashdev.nexussphere.common.auth.jwt.JwtService;
import com.biswasakashdev.nexussphere.common.auth.jwt.JwtServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@RequiredArgsConstructor
public class ServiceURLConfig {

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

    public record RegisteredServiceURLs(
            String userService,
            String workspaceService
    ) {
    }


    @Bean
    RegisteredServiceURLs registeredServiceURLs(){

        String usersURL = environment.getProperty("services.users");
        String workspaceURL = environment.getProperty("services.workspace");
        if(usersURL == null  ||  workspaceURL == null ){
            throw new IllegalStateException("Service url cant be null");
        }
        return new RegisteredServiceURLs(usersURL, workspaceURL);
    }

}
