package com.intralogix.common.services;


import com.intralogix.common.dtos.AccessToken;
import com.intralogix.common.dtos.AuthToken;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtService {


    AccessToken generateAccessToken(UserDetails userDetails, Map<String, Object> extraPayload);

    AuthToken generateToken(String userId, Boolean longAged);

    String getSubject(String token);

    UserDetails extractUserDetails(String token);

}
