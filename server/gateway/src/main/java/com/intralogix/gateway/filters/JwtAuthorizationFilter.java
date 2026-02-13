package com.intralogix.gateway.filters;

import com.intralogix.common.services.JwtService;
import com.intralogix.common.exceptions.AccountNotEnabledException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter implements GatewayFilter {

    private final JwtService jwtService;

    @NonNull
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, @NonNull GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String authorization = request.getHeaders().getFirst("Authorization");

        try {

            if (authorization == null || !authorization.startsWith("Bearer ")) {
                throw new RuntimeException("Authorization header is invalid");
            }

            String token = authorization.substring(7);
            final String userId;

        } catch (Exception ex) {

        }

        ServerHttpRequest modifiedRequest = request.mutate()
                .headers((headers) -> {
                    headers.replace("X-User-Id", List.of(userDetails.getUsername()));
                    List<String> permissions = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
                    headers.replace("X-User-Permissions", permissions);
                }).build();
        return chain.filter(exchange.mutate().request(modifiedRequest).build());

        return response.setComplete();
    }
}
