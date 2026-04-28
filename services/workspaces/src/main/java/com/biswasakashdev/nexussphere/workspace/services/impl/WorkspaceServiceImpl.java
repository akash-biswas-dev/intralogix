package com.biswasakashdev.nexussphere.workspace.services.impl;

import com.biswasakashdev.nexussphere.common.client.AccessManagerClient;
import com.biswasakashdev.nexussphere.workspace.dtos.requests.NewWorkspaceRequest;
import com.biswasakashdev.nexussphere.workspace.exceptions.DatasourceOperationFailedException;
import com.biswasakashdev.nexussphere.workspace.models.UserType;
import com.biswasakashdev.nexussphere.workspace.models.UsersOnWorkspace;
import com.biswasakashdev.nexussphere.workspace.models.Workspaces;
import com.biswasakashdev.nexussphere.workspace.repository.UsersOnWorkspaceRepository;
import com.biswasakashdev.nexussphere.workspace.repository.WorkspaceRepository;
import com.biswasakashdev.nexussphere.workspace.services.WorkspaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;


@Service
@Slf4j
@RequiredArgsConstructor
public class WorkspaceServiceImpl implements WorkspaceService {

    private final WorkspaceRepository workspaceRepository;
    private final UsersOnWorkspaceRepository usersOnWorkspaceRepository;
    private final AccessManagerClient accessManagerClient;

    @Override
    public Mono<Workspaces> createWorkspace(String userId, NewWorkspaceRequest newWorkspace) {
        Workspaces workspaces = Workspaces.builder()
                .name(newWorkspace.name())
                .description(newWorkspace.description())
                .createdOn(LocalDate.now())
                .build();

        return workspaceRepository
                .save(workspaces)
                .flatMap(savedWorkspace -> {
                    UsersOnWorkspace usersOnWorkspace = UsersOnWorkspace
                            .builder()
                            .workspaceId(workspaces.getId())
                            .userId(userId)
                            .userType(UserType.OWNER)
                            .joinedOn(LocalDate.now())
                            .build();
                    return usersOnWorkspaceRepository.save(usersOnWorkspace)
                            .then(accessManagerClient.createPrimarySecurityGroup(userId, workspaces.getId()))
                            // TODO: Add the error result checking and error handling.
                            .then();
                })
                .onErrorResume(DatasourceOperationFailedException.class, throwable -> {
                    log.error(throwable.getMessage(), throwable);
                    return Mono.error(new RuntimeException("Failed to save workspace."));
                });
    }

    @Override
    public Mono<Boolean> isWorkspaceNameExists(String workspaceId, String userId) {
        return null;
    }

    @Override
    public Mono<Page<User>> findAllUsersInWorkspace(String workspaceId, Integer page, Integer pageSize, Sort.Direction direction) {
        return null;
    }


    @Override
    public Mono<Page<Workspaces>> findAllWorkspace(String userId, Integer page, Integer pageSize, Sort.Direction direction) {
        return null;
    }
}
