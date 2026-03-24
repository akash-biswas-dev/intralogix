package com.nexussphere.workspace.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users_on_workspace")
public class UsersOnWorkspace {

    private String userId;
    private String workspaceId;

    private LocalDate joinedOn;

}
