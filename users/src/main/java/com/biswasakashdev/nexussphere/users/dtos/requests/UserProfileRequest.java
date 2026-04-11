package com.biswasakashdev.nexussphere.users.dtos.requests;

import com.biswasakashdev.nexussphere.users.models.Gender;

public record UserProfileRequest(
        String username,
        String firstName,
        String lastName,
        String dateOfBirth,
        Gender gender
){
}
