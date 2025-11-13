package com.intralogix.workspace.repository;

import com.intralogix.workspace.dtos.dao.UsersOnWorkspaceMembers;
import com.intralogix.workspace.models.UsersOnWorkspace;
import com.intralogix.workspace.models.UsersOnWorkspaceId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsersOnWorkspaceRepository extends JpaRepository<UsersOnWorkspace, UsersOnWorkspaceId> {
    Page<UsersOnWorkspaceMembers> findById_OwnerIdAndId_WorkspaceName(
            String ownedBy,
            String workspaceName,
            Pageable pageable
    );
}
