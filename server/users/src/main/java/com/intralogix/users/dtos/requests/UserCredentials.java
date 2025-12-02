package com.intralogix.users.dtos.requests;


public record UserCredentials(
        String usernameOrEmail,
        String password
) {
}
