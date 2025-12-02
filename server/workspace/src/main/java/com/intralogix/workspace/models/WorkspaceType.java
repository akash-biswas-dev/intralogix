package com.intralogix.workspace.models;


import lombok.Getter;

public enum WorkspaceType {

    IT("It"),
    ELECTRONICS("Electronics"),
    MEDICAL("Medical"),
    IMPORTS_EXPORTS("Imports Exports");

    @Getter
    private final String value;

    WorkspaceType(String value) {
        this.value = value;
    }
}
