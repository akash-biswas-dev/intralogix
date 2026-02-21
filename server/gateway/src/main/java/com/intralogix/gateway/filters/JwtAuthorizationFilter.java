package com.intralogix.gateway.filters;

import com.intralogix.common.jwt.JwtService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter implements GatewayFilter {

    private final JwtService jwtService;

    @NonNull
    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            @NonNull GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String authorization = request.getHeaders().getFirst("Authorization");

        final String userId;
        try {

            if (authorization == null || !authorization.startsWith("Bearer ")) {
                throw new RuntimeException("Authorization header is invalid");
            }
            String token = authorization.substring(7);

            userId = jwtService.getUserId(token);

        } catch (Exception ex) {
            log.error("Error while getting user id from token", ex);
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        ServerHttpRequest modifiedRequest = request.mutate()
                .headers((headers) -> headers.set("Authentication-Info",userId)).build();
        return chain.filter(exchange.mutate().request(modifiedRequest).build());
    }
}
