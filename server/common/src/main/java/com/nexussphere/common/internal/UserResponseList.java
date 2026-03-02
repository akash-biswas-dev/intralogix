package com.nexussphere.common.internal;

import com.nexussphere.common.response.UserResponse;

import java.util.List;

public record UserResponseList(
        List<UserResponse> userResponseList
) {
}
