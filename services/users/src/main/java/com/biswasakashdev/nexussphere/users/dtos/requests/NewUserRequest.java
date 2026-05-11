package com.biswasakashdev.nexussphere.users.dtos.requests;

public record NewUserRequest(
        String email,
        String password,
        String firstName,
        String lastName
) {
}
