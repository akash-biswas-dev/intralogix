package com.nexussphere.workspace.services;

import com.nexussphere.common.response.UserResponse;
import com.nexussphere.workspace.dtos.requests.NewWorkspaceRequest;
import com.nexussphere.workspace.models.Workspaces;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Mono;

public interface WorkspaceService {

    Mono<Void> createWorkspace(String userId, NewWorkspaceRequest newWorkspace);

    Mono<Boolean> isWorkspaceNameExists(String workspaceId, String userId);

    Mono<Page<UserResponse>> findAllUsersInWorkspace(String workspaceId, Integer page, Integer pageSize, Sort.Direction direction);

    Mono<Page<Workspaces>> findAllWorkspace(String userId, Integer page, Integer pageSize, Sort.Direction direction);
}
