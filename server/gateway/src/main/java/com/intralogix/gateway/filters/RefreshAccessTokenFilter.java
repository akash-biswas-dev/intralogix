package com.intralogix.gateway.filters;

import com.intralogix.common.services.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;


@Component
@Slf4j
public class RefreshAccessTokenFilter implements GatewayFilter {

    private final JwtService jwtService;

    public RefreshAccessTokenFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String token = request.getHeaders().getFirst("X-Refresh-Token");
        if (token == null) {
            ServerHttpResponse response = exchange.getResponse();
            response.getHeaders().set("WWW-Authenticate", "X-Refresh-Token");
            response.getHeaders().set("X-Message", "Token not found");
            return response.setComplete();
        }
        try{
           String userId = jwtService.getSubject(token);
           request.getHeaders().replace("X-User-Id", List.of(userId));
        }catch (ExpiredJwtException ex){
            log.error("Refresh token expired {}",ex.getMessage());
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            response.getHeaders().set("WWW-Authenticate", "X-Refresh-Token");
            response.getHeaders().set("X-Message", "Token expired");
            return response.setComplete();
        }catch (Exception ex){
            log.error("Refresh token error {}",ex.getMessage());
            ServerHttpResponse response = exchange.getResponse();
            response.getHeaders().set("WWW-Authenticate", "X-Refresh-Token");
            response.getHeaders().set("X-Message", "Token error");
            return response.setComplete();
        }
        return chain.filter(exchange);
    }
}
