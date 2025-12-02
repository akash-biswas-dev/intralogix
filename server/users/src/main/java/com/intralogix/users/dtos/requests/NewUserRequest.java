package com.intralogix.users.dtos.requests;

public record NewUserRequest(
        String username,
        String email,
        String password
) {
}
