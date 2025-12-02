package com.intralogix.workspace.services.impl;

import com.intralogix.common.response.PageResponse;
import com.intralogix.common.response.UserResponse;
import com.intralogix.workspace.dtos.requests.NewWorkspaceRequest;
import com.intralogix.workspace.dtos.response.WorkspaceResponse;
import com.intralogix.workspace.models.UsersOnWorkspace;
import com.intralogix.workspace.models.WorkspaceId;
import com.intralogix.workspace.models.Workspaces;
import com.intralogix.workspace.repository.UsersOnWorkspaceRepository;
import com.intralogix.workspace.repository.WorkspaceRepository;
import com.intralogix.workspace.services.GroupsService;
import com.intralogix.workspace.services.WorkspaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EntityServiceImpl implements WorkspaceService, GroupsService {

    private final WorkspaceRepository workspaceRepository;
    private final UsersOnWorkspaceRepository usersOnWorkspaceRepository;

    /**
     * Create a new workspace and after successfully created the workspace
     * Also add an entry on the users on workspace table.
     * @param userId The user who created the workspace.
     * @param newWorkspace Details of the new workspace.
     * @return The details of newly created workspace.
    * */
    @Override
    public WorkspaceResponse createWorkspace(String userId, NewWorkspaceRequest newWorkspace) {

        Workspaces workspace = new Workspaces(
                userId,
                newWorkspace.name(),
                newWorkspace.description(),
                newWorkspace.type(),
                LocalDate.now()
        );
        try {
            workspace = workspaceRepository.save(workspace);
        } catch (Exception ex) {
            log.error(ex.getMessage());
//            TODO: Change it to a custom exception.
            throw new RuntimeException("Some error occurred");
        }

        usersOnWorkspaceRepository.save(new UsersOnWorkspace(
                userId,
                userId,
                newWorkspace.name(),
                LocalDate.now()
        ));

        return generateWorkspaceResponse(workspace);
    }

    @Override
    public Boolean isWorkspaceNameExists(String workspaceName, String userId) {
        return isWorkspaceNameAlreadyExists(workspaceName, userId);
    }

    @Override
    public List<String> getAllUsersOfWorkspace(String workspaceId) {
        return List.of();
    }

    @Override
    public PageResponse<UserResponse> findAllUsersInWorkspace(String workspaceId,
                                                              Integer page, Integer size,
                                                              Sort.Direction direction) {
        return null;
    }


    private WorkspaceResponse generateWorkspaceResponse(Workspaces workspaces) {
        WorkspaceId id = workspaces.getWorkspaceId();
        return new WorkspaceResponse(
                id.getWorkspaceName(),
                id.getOwnerId(),
                workspaces.getCreatedOn()
        );
    }

    private boolean isWorkspaceNameAlreadyExists(String ownerId, String workspaceName) {
        return workspaceRepository.existsById(new WorkspaceId(ownerId, workspaceName));
    }

}
