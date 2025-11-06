package com.intralogix.workspace.dtos.requests;

import com.intralogix.workspace.models.GroupType;

public record NewGroupRequest(
        String groupName,
        String description,
        GroupType groupType
) {
}
