package com.intralogix.workspace.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "groups")
public class Groups {

    @EmbeddedId
    private GroupsId groupsId;

    @ManyToOne
    @MapsId(value = "workspaceId")
    @JoinColumn(name = "workspace_id")
    public Workspaces workspaces;

    @CreatedDate
    public LocalDate createdOn;

    @Enumerated(EnumType.STRING)
    public GroupType groupType;

}
