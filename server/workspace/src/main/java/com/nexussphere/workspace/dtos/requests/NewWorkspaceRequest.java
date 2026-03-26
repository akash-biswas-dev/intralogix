package com.nexussphere.workspace.dtos.requests;

import com.nexussphere.workspace.models.WorkspaceType;

public record NewWorkspaceRequest(
        String name,
        String description
) {
}
