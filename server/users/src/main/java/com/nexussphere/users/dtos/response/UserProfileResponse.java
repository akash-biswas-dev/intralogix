package com.nexussphere.users.dtos.response;

public record UserProfileResponse(
        String username,
        String email,
        String firstName,
        String lastName,
        String dateOfBirth,
        String gender,
        String joinedOn
) {
}
