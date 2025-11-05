package com.intralogix.users.models;


import lombok.Getter;

@Getter
public enum Role {
    ADMIN("Admin"),
    MANAGER("Manager"),
    USER("User");

    private final String value;

    Role(final String value) {
        this.value = value;
    }
}

