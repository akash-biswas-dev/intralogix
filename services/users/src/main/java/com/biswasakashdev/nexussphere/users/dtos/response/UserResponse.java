package com.biswasakashdev.nexussphere.users.dtos.response;

public record UserResponse(
        String username,
        String email,
        String firstName,
        String lastName
) {
}
