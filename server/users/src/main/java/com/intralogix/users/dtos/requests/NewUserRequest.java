package com.intralogix.users.dtos.requests;

public record NewUserRequest(
        String email,
        String password
) {
}
