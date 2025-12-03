package com.intralogix.gateway.config;


import com.intralogix.gateway.filters.JwtAuthorizationFilter;
import com.intralogix.gateway.filters.RefreshAccessTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final RefreshAccessTokenFilter refreshAccessTokenFilter;
    private final ApplicationConfig.RegisteredServiceURLs registeredServiceURLs;

    @Bean
    RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder, Environment environment){

        return routeLocatorBuilder.routes()
                .route("refresh-token",r->r
                        .path("/api/v1/auth/refresh-token")
                        .filters(f->f.filter(refreshAccessTokenFilter))
                        .uri(registeredServiceURLs.userService())
                )
                .route("auth-route",r->r
                        .path("/api/v1/auth/**")
                        .filters(f->f.rewritePath("/users/","/"))
                        .uri(registeredServiceURLs.userService())
                )
                .route("user-route", r-> r
                        .path("/api/v1/users/**")
                        .filters(f-> f.filter(jwtAuthorizationFilter))
                        .uri(registeredServiceURLs.userService())
                )
                .route("workspace_route", r-> r
                        .path("/api/v1/workspaces/**")
                        .filters(f -> f.filter(jwtAuthorizationFilter))
                        .uri(registeredServiceURLs.workspaceService())
                )
                .build();
    }
}
