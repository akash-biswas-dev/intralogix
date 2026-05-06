package com.biswasakashdev.nexussphere.common.dtos;

import com.biswasakashdev.nexussphere.protogen.users.v1.Types;

public record UserDetails(
        String id,
        String username,
        String firstName,
        String lastName
) {


    public static UserDetails buildUserDetails(Types.UserDetailesProto userDetailesProto) {
        return new UserDetails(
                userDetailesProto.getId(),
                userDetailesProto.getUsername(),
                userDetailesProto.getFirstName(),
                userDetailesProto.getLastName()
        );
    }
}
