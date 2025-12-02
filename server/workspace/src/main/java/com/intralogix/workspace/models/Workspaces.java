package com.intralogix.workspace.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "workspaces")
public class Workspaces {

    @EmbeddedId
    private WorkspaceId workspaceId;

    private String description;
    @Column(name = "created_on", nullable = false, updatable = false)
    private LocalDate createdOn;

    @Enumerated(EnumType.STRING)
    @Column(name = "workspace_type", nullable = false)
    private WorkspaceType workspaceType;

    public Workspaces(
            String ownedBy,
            String name,
            String description,
            WorkspaceType workspaceType,
            LocalDate createdOn) {
        this.workspaceId = new WorkspaceId(ownedBy,name);
        this.description = description;
        this.workspaceType = workspaceType;
        this.createdOn = createdOn;
    }

    public String getWorkspaceName(){
        return this.workspaceId.getWorkspaceName();
    }
    public String getOwnerId(){
        return this.workspaceId.getOwnerId();
    }
}

