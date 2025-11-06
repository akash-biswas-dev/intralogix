package com.intralogix.workspace.services;

import com.intralogix.workspace.dtos.requests.NewWorkspaceRequest;
import com.intralogix.workspace.dtos.response.WorkspaceResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface WorkspaceService {
    WorkspaceResponse createWorkspace(String userId, NewWorkspaceRequest newWorkspace);

    Boolean isWorkspaceNameExists(String workspaceId, String userId);

    List<String> getAllUsersOfWorkspace(String workspaceId);
}
