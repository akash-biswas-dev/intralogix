package com.intralogix.gateway.filters;

import com.intralogix.common.services.JwtService;
import com.intralogix.gateway.exceptions.AccountNotEnabledException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
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

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String authorization = request.getHeaders().getFirst("Authorization");

        if(authorization == null || !authorization.startsWith("Bearer ")) {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            response.getHeaders().set("WWW-Authenticate", "Bearer");
            byte[] responseBytes = "Unauthorized".getBytes();
            DataBuffer wrapped = response.bufferFactory().wrap(responseBytes);
            return response.writeWith(Mono.just(wrapped));
        }
        try {
            String token = authorization.substring(7);
            UserDetails userDetails = jwtService.extractUserDetails(token);
            if(!userDetails.isEnabled()) {
                throw new AccountNotEnabledException(userDetails.getUsername());
            }
            HttpHeaders headers = request.getHeaders();
            headers.replace("X-User-Id", List.of(userDetails.getUsername()));
            headers.replace("User-Role", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
        }catch (AccountNotEnabledException ex) {
            log.warn("Account not enabled yet with account id: {}", ex.getMessage());
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.TEMPORARY_REDIRECT);
            response.getHeaders().set(HttpHeaders.LOCATION, "/users/user-profile");
            return response.setComplete();
        }catch (ExpiredJwtException ex){
            log.error("Token expired {}",ex.getMessage());
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.PROXY_AUTHENTICATION_REQUIRED);
            return  response.setComplete();
        }
        catch (Exception ex){
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            response.getHeaders().set("WWW-Authenticate", "Bearer");
        }

        return chain.filter(exchange);
    }
}
