package com.biswasakashdev.nexussphere.workspace.repository;

import com.biswasakashdev.nexussphere.workspace.models.Workspaces;
import reactor.core.publisher.Mono;

public interface WorkspaceRepository {

    Mono<Workspaces> save(Workspaces workspaces);

    Mono<Workspaces> findById(String id);

    Mono<Boolean> deleteById(String id);

}
