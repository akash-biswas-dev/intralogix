package com.biswasakashdev.nexussphere.workspace.services.impl;

import com.biswasakashdev.nexussphere.common.client.AccessManagerClient;
import com.biswasakashdev.nexussphere.common.dtos.UserDetails;
import com.biswasakashdev.nexussphere.common.response.PageResponse;
import com.biswasakashdev.nexussphere.common.exceptions.DataSourceOperationFailedException;
import com.biswasakashdev.nexussphere.common.response.UserResponse;
import com.biswasakashdev.nexussphere.workspace.dtos.requests.NewWorkspaceRequest;
import com.biswasakashdev.nexussphere.workspace.models.UserType;
import com.biswasakashdev.nexussphere.workspace.models.UsersOnWorkspace;
import com.biswasakashdev.nexussphere.workspace.models.Workspaces;
import com.biswasakashdev.nexussphere.workspace.repository.UsersOnWorkspaceRepository;
import com.biswasakashdev.nexussphere.workspace.repository.WorkspaceRepository;
import com.biswasakashdev.nexussphere.workspace.services.WorkspaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    public Mono<Workspaces> createWorkspace(String userId, NewWorkspaceRequest newWorkspace) {
        Workspaces workspaces = Workspaces.builder()
                .name(newWorkspace.name())
                .description(newWorkspace.description())
                .createdOn(LocalDate.now())
                .build();

        return workspaceRepository
                .save(workspaces)
                .flatMap((savedWorkspace) -> {

                    UsersOnWorkspace usersOnWorkspace =
                            UsersOnWorkspace.builder()
                                    .userId(userId)
                                    .workspaceId(savedWorkspace.getId())
                                    .userType(UserType.OWNER)
                                    .build();

                    return usersOnWorkspaceRepository.save(usersOnWorkspace)
                            .flatMap((savedUsersOnWorkspace) -> accessManagerClient
                                    .createPrimarySecurityGroup(
                                            savedUsersOnWorkspace.getUserId(),
                                            savedUsersOnWorkspace.getWorkspaceId()
                                    ))
                            .then(Mono.just(savedWorkspace));
                })
                .onErrorResume(DataSourceOperationFailedException.class, (err) ->
                        Mono.error(new RuntimeException("Failed to create workspaces."))
                );
    }

    @Override
    public Mono<Boolean> isWorkspaceNameExists(String workspaceId, String userId) {
        return null;
    }

    @Override
    public Mono<PageResponse<UserDetails>> findAllUsersInWorkspace(
            String workspaceId,
            Integer page,
            Integer pageSize,
            Sort.Direction direction) {

        return usersOnWorkspaceRepository
                .findAllUsersInWorkspace(workspaceId, page,pageSize, direction)
                .map(usersOnWorkspaceInfo ->
                        );
    }


    @Override
    public Mono<PageResponse<Workspaces>> findAllWorkspace(String userId, Integer page, Integer pageSize, Sort.Direction direction) {
        return null;
    }
}
