package com.biswasakashdev.nexussphere.users.utils;

import com.biswasakashdev.nexussphere.common.response.UserResponse;
import com.biswasakashdev.nexussphere.users.dtos.response.UserProfileResponse;
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

    public static UserProfileResponse getUserProfileResponse(Users users) {
        UserProfile profile = users.getUserProfile();

        return new UserProfileResponse(
                users.getUsername(),
                users.getEmail(),
                profile.getFirstName(),
                profile.getLastName(),
                profile.getDateOfBirth().toString(),
                profile.getGender().name()
        );
    }
}
