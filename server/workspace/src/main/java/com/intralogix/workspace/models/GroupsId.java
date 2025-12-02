package com.intralogix.workspace.models;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupsId implements Serializable {

    @Column(name = "owned_by")
    private String ownerId;
    @Column(name = "workspace_name")
    private String workspaceName;
    @Column(name = "group_name")
    private String groupName;
}
