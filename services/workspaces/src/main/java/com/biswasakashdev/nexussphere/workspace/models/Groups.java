package com.biswasakashdev.nexussphere.workspace.models;


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
@Table(name = "groups")
@NoArgsConstructor
@AllArgsConstructor
public class Groups {

    @Id
    private String id;

    @Column("group_name")
    private String name;

    private String description;

    @Column("workspace_id")
    private String workspaceId;
}
