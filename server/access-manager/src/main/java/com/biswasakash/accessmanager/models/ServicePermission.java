package com.biswasakash.accessmanager.models;


import lombok.Getter;

public enum ServicePermission {

    CREATE("Create"),
    READ("Read"),
    UPDATE("Update"),
    DELETE("Delete");

    @Getter
    private final String value;

    ServicePermission(String value) {
       this.value = value;
    }
}
