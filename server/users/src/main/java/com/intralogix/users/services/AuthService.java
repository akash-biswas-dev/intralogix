package com.intralogix.users.services;

import com.intralogix.common.dtos.AccessToken;
import com.intralogix.common.dtos.AuthToken;
import com.intralogix.users.dtos.requests.UserCredentials;

public interface AuthService {
    AuthToken login(UserCredentials userCredentials, Boolean rememberMe);

    String refreshAccessToken(String userId);
}
