package com.biswasakashdev.nexussphere.users.dtos.response;


import lombok.Builder;

@Builder
public record UserProfileResponse(
        String username,
        String email,
        String firstName,
        String lastName,
        String dateOfBirth,
        String gender
) {
}
