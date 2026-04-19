package com.biswasakashdev.nexussphere.workspace.models;


import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersOnWorkspace {

    private String userId;
    private String workspaceId;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private String userType;

    private LocalDate joinedOn;

    public static class UsersOnWorkspaceBuilder {
        public UsersOnWorkspaceBuilder userType(UserType userType) {
            this.userType = userType.name();
            return this;
        }
    }

    public UserType getUserType() {
        return UserType.valueOf(this.userType);
    }

    public void  setUserType(UserType userType) {
        this.userType = userType.name();
    }

}
