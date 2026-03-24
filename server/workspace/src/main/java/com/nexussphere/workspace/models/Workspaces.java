package com.nexussphere.workspace.models;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "workspace")
public class Workspaces {

    @Id
    private String id;

    private String name;

    private String description;
    @Column("created_on")
    private LocalDate createdOn;

    @Column("owner_id")
    private String ownerId;

}

