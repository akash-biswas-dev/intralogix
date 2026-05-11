package com.biswasakashdev.nexussphere.accessmanager.models;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;


@Getter
@Setter
@Builder
@Table(name = "security_group")
public class SecurityGroup {

    @Id
    private String id;

    private String name;
    private String description;

    private String workspaceId;

    private LocalDate createdAt;
}
