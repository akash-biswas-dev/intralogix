package com.biswasakash.accessmanager.models;


import lombok.Getter;

public enum SecurityPermissions {

    CREATE("Create"),
    READ("Read"),
    UPDATE("Update"),
    DELETE("Delete");

    @Getter
    private final String value;

    SecurityPermissions(String value) {
       this.value = value;
    }
}
