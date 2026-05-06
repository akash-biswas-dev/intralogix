package com.biswasakashdev.nexussphere.workspace.models;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Workspaces {

    private String id;
    private String name;
    private String ownedBy;
    private String description;
    private LocalDate createdOn;
    private LocalDateTime lastActive;
    private Long userCount;
    private Long groupCount;

}

