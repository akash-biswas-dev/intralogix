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

    @CreatedDate
    @Column(name = "created_on", nullable = false,updatable = false)
    public LocalDate createdOn;

    @Enumerated(EnumType.STRING)
    @Column(name = "group_type")
    public GroupType groupType;

    public Groups(String ownerId, String workspaceName,String groupName,GroupType groupType) {
        this.groupsId = new GroupsId(ownerId,workspaceName,groupName);
        this.groupType = groupType;
    }

}
