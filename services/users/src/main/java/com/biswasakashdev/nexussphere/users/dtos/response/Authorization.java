package com.biswasakashdev.nexussphere.users.dtos.response;

import com.biswasakashdev.nexussphere.common.response.UserResponse;

public record Authorization(
        String token,
        long maxAge // Token valid in seconds.
) {
}
