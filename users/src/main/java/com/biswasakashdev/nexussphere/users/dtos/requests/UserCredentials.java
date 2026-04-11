package com.biswasakashdev.nexussphere.users.dtos.requests;


public record UserCredentials(
        String emailOrUsername,
        String password
) {
}
