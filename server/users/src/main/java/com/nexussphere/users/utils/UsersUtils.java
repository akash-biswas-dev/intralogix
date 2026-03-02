package com.nexussphere.users.utils;

import com.nexussphere.common.response.UserResponse;
import com.nexussphere.users.models.UserProfile;
import com.nexussphere.users.models.Users;

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
