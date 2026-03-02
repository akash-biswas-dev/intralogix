package com.nexussphere.users.dtos.response;

import com.nexussphere.common.response.UserResponse;

public record Authorization (
        String token,
        UserResponse user
){
}
