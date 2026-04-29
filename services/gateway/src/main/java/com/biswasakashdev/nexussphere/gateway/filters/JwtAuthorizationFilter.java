package com.biswasakashdev.nexussphere.gateway.filters;

import com.biswasakashdev.nexussphere.common.auth.AccountStatus;
import com.biswasakashdev.nexussphere.common.auth.jwt.JwtService;
import com.biswasakashdev.nexussphere.gateway.exceptions.ResourceNotAllowedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
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

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter implements GatewayFilter {

    private final JwtService jwtService;


    private static final List<String> ALLOWED_PATHS_ACCOUNT_INACTIVE = List.of(
            "/api/v1/users/profile",
            "/api/v1/auth/refresh-authorization"
    );

    private static final List<String> NOT_ALLOWED_PATHS_ACCOUNT_ACTIVE = List.of(
            "/api/v1/auth/refresh-authorization"
    );


    @NonNull
    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            @NonNull GatewayFilterChain chain
    ) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String authorization = request.getHeaders().getFirst("Authorization");

        try {

            if (authorization == null || !authorization.startsWith("Bearer ")) {
                throw new RuntimeException("Invalid authorization found.");
            }
            String token = authorization.substring(7);

            Claims claims = jwtService.extractAllClaims(token);

            String userId = claims.getSubject();

            String accountStatus = claims.get("account_status", String.class);


//           Prevent access the endpoints which only available when user profile created.
            if (!isResourceAllowedWhenUserAccountStatusInactive(request, accountStatus)) {
                throw new ResourceNotAllowedException(userId, "Try to access the resource when user account is inactive.");
            }

            if (!isResourceAllowedWhenUserAccountStatusActive(request,accountStatus)) {
                throw new ResourceNotAllowedException(userId, "Try to access the resource when user account is active.");
            }

            ServerHttpRequest modifiedRequest = request.mutate()
                    .headers((headers) -> headers.set("Authentication-Info", userId)).build();

            return chain.filter(exchange.mutate().request(modifiedRequest).build());

        } catch (ResourceNotAllowedException ex) {
            log.error("Resource not allowed for user with id: {} at {} with message: {}", ex.getUserId(), request.getPath(), ex.getMessage());
            response.setStatusCode(HttpStatus.FORBIDDEN);
            response.getHeaders().set("WWW-Authenticate", "Resource not allowed.");
            return response.setComplete();
        } catch (ExpiredJwtException expiredJwtException) {
            log.error("Authorization expired for user: {}", expiredJwtException.getClaims().getSubject());
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token passed with message: {}", ex.getMessage());
        } catch (Exception ex) {
            log.error("Error occurred while authenticating user with message: {}", ex.getMessage());
        }
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().set("WWW-Authenticate", "Invalid authorization found.");
        return response.setComplete();
    }

    private boolean isResourceAllowedWhenUserAccountStatusActive(
            ServerHttpRequest request,
            String accountStatus
    ) {
        String currentPath = request.getPath().value();
        boolean isPresent = NOT_ALLOWED_PATHS_ACCOUNT_ACTIVE.stream().anyMatch(allowedPath -> allowedPath.equals(currentPath)) ;
        return isPresent && accountStatus.equals(AccountStatus.ACTIVE.name());
    }


    private static boolean isResourceAllowedWhenUserAccountStatusInactive(
            ServerHttpRequest req,
            String accountStatus
    ) {
        String currentPath = req.getPath().value();
        boolean isPresent = ALLOWED_PATHS_ACCOUNT_INACTIVE.stream().anyMatch(allowedPath -> allowedPath.equals(currentPath));

        return isPresent && accountStatus.equals(AccountStatus.INACTIVE.name());
    }


}
