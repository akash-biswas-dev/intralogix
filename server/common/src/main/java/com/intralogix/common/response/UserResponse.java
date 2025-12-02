package com.intralogix.common.response;

import java.time.LocalDate;

public record UserResponse(
        String username,
        LocalDate joinedOn
) {
}
