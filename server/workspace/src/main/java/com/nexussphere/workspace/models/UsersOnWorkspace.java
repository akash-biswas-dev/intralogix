package com.nexussphere.workspace.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users_on_workspace")
public class UsersOnWorkspace {

    @Column("user_id")
    private String userId;
    @Column("workspace_id")
    private String workspaceId;

    @Column("joined_on")
    private LocalDate joinedOn;

}
