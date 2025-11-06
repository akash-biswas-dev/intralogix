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
public class UsersOnWorkspaceId implements Serializable {

    private UUID workspaceId;
    @Column(name = "user_id")
    private String userId;
}
