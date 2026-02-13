package com.intralogix.common.services.impl;

import com.intralogix.common.services.JwtService;
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

    private final String secret;
    private final String issuer;

    public JwtServiceImpl(String secret, String issuer) {
        this.secret = secret;
        this.issuer = issuer;
    }

    @Override
    public String generateToken(String userId, Integer expiryInSeconds) {
        return buildToken(userId,new HashMap<>(), expiryInSeconds * 1000L);
    }

    @Override
    public String getUserId(String token) {
        return extractClaim(token, Claims::getSubject);
    }



    private String buildToken(String subject, Map<String, Object> claims, Long expiredAfter) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(issuer)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiredAfter))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

}
