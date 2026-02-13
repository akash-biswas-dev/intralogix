package com.intralogix.gateway.filters;

import com.intralogix.common.services.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
@Slf4j
public class RefreshAccessTokenFilter implements GatewayFilter {

    private final JwtService jwtService;

    public RefreshAccessTokenFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @NonNull
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, @NonNull GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String token = request.getHeaders().getFirst("X-Refresh-Token");

        try {
            if (token == null) {
                throw new RuntimeException("Null token");
            }
            String userId = jwtService.getUserId(token);
            ServerHttpRequest updatedRequest = request
                    .mutate()
                    .headers((headers) -> {
                        headers.set("X-User-Id", userId);
                    }).build();
            log.info("Refreshing access token for user {}", userId);
            return chain.filter(exchange.mutate().request(updatedRequest).build());
        } catch (ExpiredJwtException ex) {
            log.error("Refresh token expired {}", ex.getMessage());
            response.getHeaders().set("X-Message", "Token expired");
        } catch (Exception ex) {
            log.error("Refresh token error with message: {}", ex.getMessage());
            response.getHeaders().set("X-Message", "Token error");
        }
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().set("WWW-Authenticate", "X-Refresh-Token");
        return response.setComplete();
    }
}
