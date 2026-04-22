package com.biswasakashdev.nexussphere.workspace.dtos.requests;

import com.biswasakashdev.nexussphere.workspace.models.GroupType;

public record NewGroupRequest(
        String groupName,
        String description,
        GroupType groupType
) {
}
