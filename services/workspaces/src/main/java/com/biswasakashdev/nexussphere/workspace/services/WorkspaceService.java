package com.biswasakashdev.nexussphere.workspace.services;

import com.biswasakashdev.nexussphere.common.dtos.UserDetails;
import com.biswasakashdev.nexussphere.common.response.PageResponse;
import com.biswasakashdev.nexussphere.common.response.UserResponse;
import com.biswasakashdev.nexussphere.workspace.dtos.requests.NewWorkspaceRequest;
import com.biswasakashdev.nexussphere.workspace.models.Workspaces;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Mono;

public interface WorkspaceService {

    Mono<Workspaces> createWorkspace(String userId, NewWorkspaceRequest newWorkspace);

    Mono<Boolean> isWorkspaceNameExists(String userId, String workspaceName);

    Mono<PageResponse<UserDetails>> findAllUsersInWorkspace(String workspaceId, Integer page, Integer pageSize, Sort.Direction direction);

    Mono<PageResponse<Workspaces>> findAllWorkspace(String userId, Integer page, Integer pageSize, Sort.Direction direction);
}
