package com.biswasakashdev.nexussphere.users.dtos.response;

public record Authorization(
        String token,
        long maxAge // Token valid in seconds.
) {
}
