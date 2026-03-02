package com.nexussphere.users.dtos.requests;


public record UserCredentials(
        String emailOrUsername,
        String password
) {
}
