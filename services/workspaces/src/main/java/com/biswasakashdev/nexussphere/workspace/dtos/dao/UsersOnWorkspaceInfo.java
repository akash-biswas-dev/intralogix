package com.biswasakashdev.nexussphere.workspace.dtos.dao;

import com.biswasakashdev.nexussphere.workspace.models.UserType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UsersOnWorkspaceInfo(
        String userId,
        UserType userType,
        LocalDateTime lastActivated
){
}
