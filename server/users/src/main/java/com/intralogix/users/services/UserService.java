package com.intralogix.users.services;

import com.intralogix.users.dtos.requests.NewUserRequest;
import com.intralogix.users.dtos.requests.UserProfileDTO;
import com.intralogix.users.dtos.response.UserProfileResponse;
import com.intralogix.users.dtos.response.UserResponse;

public interface UserService {

    UserResponse saveUser(NewUserRequest newUser);

    UserProfileResponse updateUserDetails(String userId, UserProfileDTO userProfileDetails);

    UserProfileResponse getUserProfile(String userId);
}
