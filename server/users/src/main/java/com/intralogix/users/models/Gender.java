package com.intralogix.users.models;

import lombok.Getter;

@Getter
public enum Gender {
    MALE("Male"),
    FEMALE("Female"),
    OTHERS("Others");

    private final String value;
    Gender(String value) {
        this.value = value;
    }
}
