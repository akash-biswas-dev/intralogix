package com.intralogix.users.dtos.requests;

import com.intralogix.users.models.Gender;

public record UserProfileRequest(
        String firstName,
        String lastName,
        Gender gender
){
}
