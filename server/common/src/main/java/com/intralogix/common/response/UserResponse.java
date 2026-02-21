package com.intralogix.common.response;

public record UserResponse(
        String username,
        String firstName,
        String lastName
) {
}
