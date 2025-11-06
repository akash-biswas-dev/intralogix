package com.intralogix.workspace.models;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "workspaces")
public class Workspaces {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "workspace_name")
    private String name;
    private String description;
    @Column(name = "owned_by")
    private String ownedBy;
    @Column(name = "created_on")
    private LocalDate createdOn;
}

