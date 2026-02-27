package com.intralogix.users.utils;

import com.intralogix.common.response.UserResponse;
import com.intralogix.users.models.UserProfile;
import com.intralogix.users.models.Users;

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
