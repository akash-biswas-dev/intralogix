package com.nexussphere.workspace.repository;

import com.nexussphere.workspace.models.WorkspaceId;
import com.nexussphere.workspace.models.Workspaces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspaces, WorkspaceId> {

}
