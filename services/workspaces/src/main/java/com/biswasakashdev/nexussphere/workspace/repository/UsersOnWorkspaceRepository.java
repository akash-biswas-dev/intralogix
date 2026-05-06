package com.biswasakashdev.nexussphere.workspace.repository;

import com.biswasakashdev.nexussphere.common.response.PageResponse;
import com.biswasakashdev.nexussphere.workspace.dtos.dao.UsersOnWorkspaceInfo;
import com.biswasakashdev.nexussphere.workspace.models.UsersOnWorkspace;
import com.biswasakashdev.nexussphere.workspace.models.Workspaces;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Mono;

public interface UsersOnWorkspaceRepository {

    Mono<UsersOnWorkspace> save(UsersOnWorkspace usersOnWorkspace);

    Mono<PageResponse<UsersOnWorkspaceInfo>> findAllUsersInWorkspace(String workspaceId, Integer requiredPage, Integer pageSize, Sort.Direction direction);

    Mono<PageResponse<Workspaces>> findAllWorkspacesWithUserId(String userId, Integer requiredPage, Integer pageSize, Sort.Direction direction);

}
