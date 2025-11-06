package com.intralogix.workspace.dtos.response;

import java.time.LocalDate;

public record WorkspaceResponse(
        String name,
        String ownedBy,
        LocalDate createdOn
) {
}
