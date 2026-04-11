package com.biswasakashdev.nexussphere.common.internal;

import com.biswasakashdev.nexussphere.common.response.UserResponse;

import java.util.List;

public record UserResponseList(
        List<UserResponse> userResponseList
) {
}
