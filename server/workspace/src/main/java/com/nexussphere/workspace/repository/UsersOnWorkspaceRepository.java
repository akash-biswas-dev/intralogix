package com.nexussphere.workspace.repository;

import com.nexussphere.workspace.models.UsersOnWorkspace;
import reactor.core.publisher.Mono;

public interface UsersOnWorkspaceRepository {

    Mono<UsersOnWorkspace> save(UsersOnWorkspace usersOnWorkspace);

}
