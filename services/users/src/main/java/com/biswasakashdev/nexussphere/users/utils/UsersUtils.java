package com.biswasakashdev.nexussphere.users.utils;

import com.biswasakashdev.nexussphere.common.response.UserResponse;
import com.biswasakashdev.nexussphere.users.models.UserProfile;
import com.biswasakashdev.nexussphere.users.models.Users;

public class UsersUtils {

    public static UserResponse getUserResponse(Users users) {
        UserProfile profile = users.getUserProfile();
        return new UserResponse(
                users.getUsername(),
                profile.getFirstName(),
                profile.getLastName()
        );
    }
}
