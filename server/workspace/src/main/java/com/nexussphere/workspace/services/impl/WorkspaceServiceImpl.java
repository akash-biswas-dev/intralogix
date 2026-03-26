package com.nexussphere.workspace.services.impl;

import com.nexussphere.common.response.UserResponse;
import com.nexussphere.workspace.dtos.requests.NewWorkspaceRequest;
import com.nexussphere.workspace.models.Workspaces;
import com.nexussphere.workspace.repository.WorkspaceRepository;
import com.nexussphere.workspace.services.WorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;


@Service
@RequiredArgsConstructor
public class WorkspaceServiceImpl implements WorkspaceService {

    private final WorkspaceRepository workspaceRepository;

    @Override
    public Mono<Void> createWorkspace(String userId, NewWorkspaceRequest newWorkspace) {
        Workspaces workspaces = Workspaces.builder()
                .name(newWorkspace.name())
                .description(newWorkspace.description())
                .ownerId(userId)
                .createdOn(LocalDate.now())
                .build();
        return workspaceRepository
                .createWorkspace(workspaces)
                .then();
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
