package com.intralogix.workspace.services.impl;

import com.intralogix.workspace.dtos.requests.NewWorkspaceRequest;
import com.intralogix.workspace.dtos.response.WorkspaceResponse;
import com.intralogix.workspace.models.Workspaces;
import com.intralogix.workspace.repository.WorkspaceRepository;
import com.intralogix.workspace.services.GroupsService;
import com.intralogix.workspace.services.WorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EntityServiceImpl implements WorkspaceService, GroupsService {

    private final WorkspaceRepository workspaceRepository;

    @Override
    public WorkspaceResponse createWorkspace(String userId, NewWorkspaceRequest newWorkspace) {
        if(isWorkspaceNameAlreadyExists(newWorkspace.name(), userId)) {
            throw new DuplicateKeyException("Workspace already exists");
        }
        Workspaces workspace = Workspaces.builder()
                .name(newWorkspace.name())
                .description(newWorkspace.description())
                .ownedBy(userId)
                .createdOn(LocalDate.now())
                .build();
        Workspaces savedWorkspace = workspaceRepository.save(workspace);
        return generateWorkspaceResponse(savedWorkspace);
    }

    @Override
    public Boolean isWorkspaceNameExists(String workspaceName, String userId) {
        return isWorkspaceNameAlreadyExists(workspaceName, userId);
    }

    @Override
    public List<String> getAllUsersOfWorkspace(String workspaceId) {
        return List.of();
    }


    private WorkspaceResponse generateWorkspaceResponse(Workspaces workspaces) {
        return new WorkspaceResponse(
                workspaces.getName(),
                workspaces.getOwnedBy(),
                workspaces.getCreatedOn()
        );
    }

    private boolean isWorkspaceNameAlreadyExists(String workspaceName, String userId) {
        return workspaceRepository.exists(Example.of(Workspaces.builder()
                        .name(workspaceName)
                .ownedBy(workspaceName)
                .build()));
    }

}
