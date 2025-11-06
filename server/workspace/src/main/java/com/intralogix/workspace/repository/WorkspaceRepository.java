package com.intralogix.workspace.repository;

import com.intralogix.workspace.models.Workspaces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspaces, UUID> {
}
