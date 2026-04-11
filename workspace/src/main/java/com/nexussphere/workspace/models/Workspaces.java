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
public class Workspaces {

    @Id
    private String id;

    private String name;

    private String description;
    private LocalDate createdOn;

}

