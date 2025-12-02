package com.intralogix.users.services;

import com.intralogix.users.dtos.requests.NewUserRequest;
import com.intralogix.users.dtos.requests.UserProfileRequest;
import com.intralogix.users.dtos.response.UserProfileResponse;
import com.intralogix.common.response.UserResponse;
import com.intralogix.users.models.Users;

import java.util.List;

public interface UserService {

    Users findUserByEmailOrUsername(String emailOrUsername);

    UserResponse saveUser(NewUserRequest newUser);

    UserProfileResponse updateUserDetails(String userId, UserProfileRequest userProfileDetails);

    UserProfileResponse getUserProfile(String userId);

    List<UserResponse> getAllUsersWithId(List<String> strings);
}
