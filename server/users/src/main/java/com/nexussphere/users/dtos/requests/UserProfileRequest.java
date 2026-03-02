package com.nexussphere.users.dtos.requests;

import com.nexussphere.users.models.Gender;

public record UserProfileRequest(
        String firstName,
        String lastName,
        String dateOfBirth,
        Gender gender
){
}
