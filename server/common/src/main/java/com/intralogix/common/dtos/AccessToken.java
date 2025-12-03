package com.intralogix.common.dtos;

public record AccessToken(
        String token,
        Boolean accountEnabled
) {
}
