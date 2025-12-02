package com.intralogix.workspace.dtos.requests;

import com.intralogix.workspace.models.WorkspaceType;

public record NewWorkspaceRequest(
        String name,
        String description,
        WorkspaceType type
) {
}
