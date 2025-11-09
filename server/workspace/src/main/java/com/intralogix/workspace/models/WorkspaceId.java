package com.intralogix.workspace.models;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkspaceId implements Serializable {
    @Column(name = "owned_by")
    private String ownerId;
    @Column(name = "workspace_name")
    private String workspaceName;
}
