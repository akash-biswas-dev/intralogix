package com.intralogix.users.dtos.response;


public record AuthTokens(
    String accessToken,
    String refreshToken
) {
}
