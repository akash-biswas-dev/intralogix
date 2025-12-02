package com.intralogix.workspace.services;

import com.intralogix.common.response.PageResponse;
import com.intralogix.common.response.UserResponse;
import com.intralogix.workspace.dtos.requests.NewWorkspaceRequest;
import com.intralogix.workspace.dtos.response.WorkspaceResponse;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface WorkspaceService {

    WorkspaceResponse createWorkspace(String userId, NewWorkspaceRequest newWorkspace);

    Boolean isWorkspaceNameExists(String workspaceId, String userId);

    List<String> getAllUsersOfWorkspace(String workspaceId);

    PageResponse<UserResponse> findAllUsersInWorkspace(String workspaceId, Integer page, Integer size, Sort.Direction direction);

}
