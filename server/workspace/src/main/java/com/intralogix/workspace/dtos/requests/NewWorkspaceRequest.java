package com.intralogix.workspace.dtos.requests;

public record NewWorkspaceRequest(
        String name,
        String description
) {
}
