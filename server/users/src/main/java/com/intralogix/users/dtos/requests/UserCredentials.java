package com.intralogix.users.dtos.requests;


public record UserCredentials(
        String emailOrUsername,
        String password
) {
}
