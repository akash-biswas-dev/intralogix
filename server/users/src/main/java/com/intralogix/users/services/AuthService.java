package com.intralogix.users.services;

import com.intralogix.users.dtos.requests.UserCredentials;
import com.intralogix.users.dtos.response.AuthTokens;

public interface AuthService {
    AuthTokens login(UserCredentials userCredentials);
}
