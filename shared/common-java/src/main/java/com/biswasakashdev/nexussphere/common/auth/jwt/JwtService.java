package com.biswasakashdev.nexussphere.common.auth.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

import java.time.Duration;
import java.util.Map;

public interface JwtService {

    String generateSession(String userId, Duration expiry);

    String generateAuthorization(String userId, Map<String,Object> extraClaims);

    String getUserId(String token) throws ExpiredJwtException, MalformedJwtException;

    Claims extractAllClaims(String token) throws ExpiredJwtException, MalformedJwtException;

    <T> T extractClaim(String token, String key, Class<T> type);
}
