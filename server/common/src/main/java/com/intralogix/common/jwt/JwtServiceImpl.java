package com.intralogix.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class JwtServiceImpl implements JwtService {

    //    In minutes.
    private static final int DEFAULT_EXPIRY = 5;

    //    Jwt secret.
    private final String secret;
    private final String issuer;

    public JwtServiceImpl(
            String secret,
            String issuer) {
        this.secret = secret;
        this.issuer = issuer;
    }

    private String createToken(String id, int expiryInMinutes, Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(id)
                .setIssuer(issuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * expiryInMinutes))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String generateSession(String userId, Integer expiryInMinutes) {
        return createToken(userId,expiryInMinutes, new HashMap<>());
    }

    @Override
    public String generateAuthorization(String userId, Map<String, Object> extraClaims) {
        return createToken(userId,DEFAULT_EXPIRY,extraClaims);
    }

    @Override
    public String getUserId(String token) {
        return extractAllClaims(token).getSubject();
    }

    @Override
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public <T> T extractClaim(String token, String key, Class<T> type){
        final Claims allClaims = extractAllClaims(token);
        Function<Claims,T> claimsResolvers = (claims)-> claims.get(key,type);
        return claimsResolvers.apply(allClaims);
    }
}
