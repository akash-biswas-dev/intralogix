package com.nexussphere.workspace.services.impl;

import com.nexussphere.common.response.UserResponse;
import com.nexussphere.workspace.dtos.requests.NewWorkspaceRequest;
import com.nexussphere.workspace.models.Workspaces;
import com.nexussphere.workspace.services.WorkspaceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public class WorkspaceServiceImpl implements WorkspaceService {

    @Override
    public Mono<Void> createWorkspace(String userId, NewWorkspaceRequest newWorkspace) {
        return null;
    }

    @Override
    public Mono<Boolean> isWorkspaceNameExists(String workspaceId, String userId) {
        return null;
    }

    @Override
    public Mono<Page<UserResponse>> findAllUsersInWorkspace(String workspaceId, Integer page, Integer pageSize, Sort.Direction direction) {
        return null;
    }

    @Override
    public Mono<Page<Workspaces>> findAllWorkspace(String userId, Integer page, Integer pageSize, Sort.Direction direction) {
        return null;
    }
}
