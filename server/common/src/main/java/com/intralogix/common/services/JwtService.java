package com.intralogix.common.services;


public interface JwtService {

    String generateToken(String userId, Integer expiryInSeconds);

    String getUserId(String token);

}
