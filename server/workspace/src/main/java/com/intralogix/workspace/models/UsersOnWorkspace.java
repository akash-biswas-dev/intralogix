package com.intralogix.workspace.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Entity
@Table(name = "users_on_workspace")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersOnWorkspace {

    @EmbeddedId
    private UsersOnWorkspaceId id;

    @CreatedDate
    @Column(name = "joined_on", updatable = false, nullable = false)
    private LocalDate joinedOn;

    public UsersOnWorkspace(
            String userId,
            String ownerId,
            String workspaceName,
            LocalDate joinedOn
            ) {
        this.id = new UsersOnWorkspaceId(
                userId,
                ownerId,
                workspaceName
        );
        this.joinedOn = joinedOn;
    }

    public String getOwnedBy(){
        return this.id.getOwnerId();
    }
    public String getWorkspaceName(){
        return this.id.getWorkspaceName();
    }
    public  String getUserId(){
        return this.id.getUserId();
    }
}
