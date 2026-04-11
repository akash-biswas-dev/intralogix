package com.nexussphere.workspace.repository;

import com.nexussphere.workspace.models.Workspaces;
import reactor.core.publisher.Mono;

public interface WorkspaceRepository {

    Mono<Workspaces> save(Workspaces workspaces);

}
