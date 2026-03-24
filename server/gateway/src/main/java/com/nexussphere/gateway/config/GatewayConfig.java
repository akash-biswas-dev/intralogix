package com.nexussphere.gateway.config;


import com.nexussphere.gateway.filters.JwtAuthorizationFilter;
import com.nexussphere.gateway.filters.CookieAuthorizationFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
public class GatewayConfig {

    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final ServiceURLConfig.RegisteredServiceURLs registeredServiceURLs;
    private final CookieAuthorizationFilter refreshTokenFilter;
    private final CookieAuthorizationFilter profileUpdateTokenFilter;


    public GatewayConfig(
            JwtAuthorizationFilter jwtAuthorizationFilter,
            ServiceURLConfig.RegisteredServiceURLs registeredServiceURLs,
            @Qualifier(value = "generateAuthorizationFilter") CookieAuthorizationFilter refreshTokenFilter,
            @Qualifier(value = "profileUpdateTokenFilter") CookieAuthorizationFilter profileUpdateTokenFilter
    ) {
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
        this.registeredServiceURLs = registeredServiceURLs;
        this.refreshTokenFilter = refreshTokenFilter;
        this.profileUpdateTokenFilter = profileUpdateTokenFilter;
    }


    @Bean
    RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder) {

        return routeLocatorBuilder.routes()
                .route("generate-authorization", r -> r
                        .method(HttpMethod.POST)
                        .and()
                        .path("/api/v1/auth/generate-authorization")
                        .filters(f -> f.filter(refreshTokenFilter))
                        .uri(registeredServiceURLs.userService())
                )
                .route("profile-update-token", r -> r
                        .method(HttpMethod.GET, HttpMethod.POST)
                        .and()
                        .path("/api/v1/auth/setup-profile")
                        .filters(f -> f.filter(profileUpdateTokenFilter))
                        .uri(registeredServiceURLs.userService())
                )
                .route("auth", (r) -> r
                        .path("/api/v1/auth/**")
                        .uri(registeredServiceURLs.userService())
                )
                .route("user-route", r -> r
                        .path("/api/v1/users/**")
                        .filters(f -> f.filter(jwtAuthorizationFilter))
                        .uri(registeredServiceURLs.userService())
                )
                .route("secured-endpoint", r -> r
                        .method(HttpMethod.GET)
                        .and()
                        .path("/api/v1/secured")
                        .filters(f->f.filter(jwtAuthorizationFilter))
                        .uri(registeredServiceURLs.userService())
                )
                .route("workspace_route", r -> r
                        .path(
                                "/api/v1/workspaces/**"
                        )
                        .filters(f -> f.filter(jwtAuthorizationFilter))
                        .uri(registeredServiceURLs.workspaceService())
                )
                .build();
    }
}
