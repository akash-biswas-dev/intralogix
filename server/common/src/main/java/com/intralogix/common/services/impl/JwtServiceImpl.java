package com.intralogix.common.services.impl;

import com.intralogix.common.dtos.AccessToken;
import com.intralogix.common.dtos.AuthToken;
import com.intralogix.common.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

public class JwtServiceImpl implements JwtService {

    private final String secret;
    private final String issuer;
    private final Long expiration;
    private final Long refreshExpiration;

    private static final String AUTHORITIES = "authorities";
    private static final String ACCOUNT_ENABLED = "accountEnabled";
    private static final String ACCOUNT_NON_EXPIRED = "accountNonExpired";
    private static final String CREDENTIALS_NON_EXPIRED = "credentialsNonExpired";
    private static final String ACCOUNT_NON_LOCKED = "accountNonLocked";

    public JwtServiceImpl(String secret, String issuer) {
        this.secret = secret;
        this.issuer = issuer;
        this.expiration = 1000L * 60 * 60 * 24;
        this.refreshExpiration = 1000L * 60 * 60 * 24 * 15;
    }

    public JwtServiceImpl(String secret, Long expiration, String issuer) {
        this.secret = secret;
        this.expiration = expiration;
        this.refreshExpiration = expiration;
        this.issuer = issuer;
    }

    public JwtServiceImpl(String secret, Long expiration, Long refreshExpiration, String issuer) {
        this.secret = secret;
        this.expiration = expiration;
        this.refreshExpiration = refreshExpiration;
        this.issuer = issuer;
    }

    @Override
    public UserDetails extractUserDetails(String token) {
        Claims claims = extractAllClaims(token);
        String userId = claims.getSubject();

        boolean enabled = claims.get(ACCOUNT_ENABLED, Boolean.class);
        boolean accountNonExpired = claims.get(ACCOUNT_NON_EXPIRED, Boolean.class);
        boolean credentialsNonExpired = claims.get(CREDENTIALS_NON_EXPIRED, Boolean.class);
        boolean accountNonLocked = claims.get(ACCOUNT_NON_LOCKED, Boolean.class);

        @SuppressWarnings("unchecked")
        List<String> authorityList = (List<String>) claims.get(AUTHORITIES, List.class);
        List<SimpleGrantedAuthority> authorities = authorityList.stream().map(SimpleGrantedAuthority::new).toList();
        return new User(userId, "no_password", enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
                authorities);
    }


    @Override
    public AccessToken generateAccessToken(UserDetails userDetails, Map<String, Object> extraClaims) {
        Map<String, Object> extraPayload = new HashMap<>();

        List<String> authorities = getAuthorities(userDetails.getAuthorities());
        extraPayload.put(AUTHORITIES, authorities);
        extraPayload.put(ACCOUNT_ENABLED, userDetails.isEnabled());
        extraPayload.put(ACCOUNT_NON_EXPIRED, userDetails.isAccountNonExpired());
        extraPayload.put(CREDENTIALS_NON_EXPIRED, userDetails.isCredentialsNonExpired());
        extraPayload.put(ACCOUNT_NON_LOCKED, userDetails.isAccountNonLocked());

        extraClaims.keySet().forEach(eachKey -> extraPayload.put(eachKey, extraClaims.get(eachKey)));
        String token = buildToken(userDetails.getUsername(), extraPayload, expiration);
        return new AccessToken(token,userDetails.isEnabled());
    }

    @Override
    public AuthToken generateToken(String userId, Boolean longAged) {
        long expiration = longAged ? this.refreshExpiration : this.expiration;
        String token = buildToken(userId, new HashMap<>(), expiration);
        return new AuthToken(token, (int)expiration/1000);
    }

    @Override
    public String getSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private static List<String> getAuthorities(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream().map(GrantedAuthority::getAuthority).toList();
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
