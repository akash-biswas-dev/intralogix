package com.intralogix.common.jwt;


import io.jsonwebtoken.Claims;

import java.util.Map;

public interface JwtService {

    String generateSession(String userId, Integer expiryInMinutes);

    String generateAuthorization(String userId, Map<String,Object> extraClaims);

    String getUserId(String token);

    Claims extractAllClaims(String token);

    <T> T extractClaim(String token, String key, Class<T> type);
}
