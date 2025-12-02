package com.intralogix.workspace.dtos.response;

public record GroupResponse(
        String groupName,
        Long members
) {
}
