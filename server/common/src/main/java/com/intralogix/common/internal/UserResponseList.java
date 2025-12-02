package com.intralogix.common.internal;

import com.intralogix.common.response.UserResponse;

import java.util.List;

public record UserResponseList(
        List<UserResponse> userResponseList
) {
}
