package com.intralogix.gateway.filters;

import com.intralogix.common.jwt.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Slf4j
public class CookieAuthorizationFilter implements GatewayFilter {

    private final JwtService jwtService;
    private final String cookieName;

    public CookieAuthorizationFilter(JwtService jwtService, String cookieName) {
        this.jwtService = jwtService;
        this.cookieName = cookieName;
    }

    @NonNull
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, @NonNull GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        HttpCookie cookie = request.getCookies().getFirst(cookieName);


        if (cookie == null) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.writeWith(
                    Mono.just(exchange.getResponse()
                            .bufferFactory()
                            .wrap("Session cookie missing".getBytes()))
            );
        }
        try {
            String userId = jwtService.getUserId(cookie.getValue());
            ServerHttpRequest updatedRequest = request
                    .mutate()
                    .headers((headers) -> {
                        headers.set("Authentication-Info", userId);
                    }).build();
            log.info("Refreshing access token for user {}", userId);
            return chain.filter(exchange.mutate().request(updatedRequest).build());
        } catch (ExpiredJwtException ex) {
            log.error("Refresh token expired {}", ex.getMessage());
            response.getHeaders().set("WWW-Authenticate", "Token expired");
        } catch (Exception ex) {
            log.error("Refresh token error with message: {}", ex.getMessage());
            response.getHeaders().set("WWW-Authenticate", "Token error");
        }

        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }
}
