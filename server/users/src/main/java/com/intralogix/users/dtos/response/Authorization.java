package com.intralogix.users.dtos.response;

import com.intralogix.common.response.UserResponse;

public record Authorization (
        String token,
        UserResponse user
){
}
