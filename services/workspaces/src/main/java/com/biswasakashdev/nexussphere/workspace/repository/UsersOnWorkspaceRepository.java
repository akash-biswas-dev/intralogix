package com.biswasakashdev.nexussphere.workspace.repository;

import com.biswasakashdev.nexussphere.workspace.models.UsersOnWorkspace;
import reactor.core.publisher.Mono;

public interface UsersOnWorkspaceRepository {

    Mono<UsersOnWorkspace> save(UsersOnWorkspace usersOnWorkspace);

}
