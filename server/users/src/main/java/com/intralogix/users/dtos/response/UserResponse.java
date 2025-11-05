package com.intralogix.users.dtos.response;

import java.time.LocalDate;

public record UserResponse(
        String username,
        LocalDate joinedOn
) {
}
