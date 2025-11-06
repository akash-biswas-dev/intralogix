package com.intralogix.workspace.models;


import com.intralogix.workspace.dtos.response.WorkspaceResponse;
import jakarta.persistence.*;

@Entity
@Table(name = "users_on_workspace")
public class UsersOnWorkspace {

    @EmbeddedId
    private UsersOnWorkspaceId id;

    @ManyToOne
    @JoinColumn(name = "workspace_id")
    @MapsId(value = "workspaceId")
    private Workspaces workspaces;
}
