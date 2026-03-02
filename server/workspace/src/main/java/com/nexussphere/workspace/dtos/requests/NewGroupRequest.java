package com.nexussphere.workspace.dtos.requests;

import com.nexussphere.workspace.models.GroupType;

public record NewGroupRequest(
        String groupName,
        String description,
        GroupType groupType
) {
}
