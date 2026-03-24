package com.nexussphere.workspace.repository;

import com.nexussphere.workspace.models.Workspaces;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkspaceRepository extends ReactiveCrudRepository<Workspaces, String> {

}
