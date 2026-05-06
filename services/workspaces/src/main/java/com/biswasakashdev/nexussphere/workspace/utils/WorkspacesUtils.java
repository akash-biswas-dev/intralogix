package com.biswasakashdev.nexussphere.workspace.utils;

import com.biswasakashdev.nexussphere.workspace.dtos.response.WorkspaceResponse;
import com.biswasakashdev.nexussphere.workspace.models.Workspaces;

public class WorkspacesUtils {
    public static WorkspaceResponse buildWorkspaceResponse(Workspaces workspaces) {
        return new WorkspaceResponse(
               workspaces.getName(),
               workspaces.getOwnedBy(),
               workspaces.getCreatedOn()
        );
    }
}
