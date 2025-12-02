package com.intralogix.users.services.impl;

import com.intralogix.common.response.UserResponse;
import com.intralogix.common.services.JwtService;
import com.intralogix.users.dtos.requests.NewUserRequest;
import com.intralogix.users.dtos.requests.UserProfileRequest;
import com.intralogix.users.dtos.response.UserProfileResponse;
import com.intralogix.users.exception.DatabaseOperationException;
import com.intralogix.users.exception.UserNotFoundException;
import com.intralogix.users.models.Role;
import com.intralogix.users.models.UserProfile;
import com.intralogix.users.models.Users;
import com.intralogix.users.repository.UsersRepository;
import com.intralogix.users.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Users findUserByEmailOrUsername(String emailOrUsername) {
        return usersRepository
                .findByEmailOrUsernameIgnoreCase(emailOrUsername, emailOrUsername)
                .orElseThrow();
    }

    @Override
    public UserResponse saveUser(NewUserRequest userRequest) {
        Users newUser = Users.builder()
                .email(userRequest.email())
                .username(userRequest.username())
                .password(passwordEncoder.encode(userRequest.password()))
                .joinedOn(LocalDate.now())
                .isAccountEnabled(false)
                .isAccountNonExpired(true)
                .role(Role.USER)
                .isAccountNonLocked(true)
                .build();
        try {
            usersRepository.save(newUser);
        } catch (Exception e) {
            log.error("Error saving user: {}", e.getMessage());
        }
        return generateUserResponse(newUser);
    }

    @Override
    public UserProfileResponse updateUserDetails(String userId, UserProfileRequest userProfileDetails) {
        final Users savedUser = fetchUserFromDBUsingUserId(userId);

        UserProfile newUserProfile = UserProfile.builder()
                .firstName(userProfileDetails.firstName())
                .lastName(userProfileDetails.lastName())
                .gender(userProfileDetails.gender())
                .build();
        savedUser.setUserProfile(newUserProfile);
        savedUser.setIsAccountEnabled(true);

        final Users updatedUser;
        try {
            updatedUser = usersRepository.save(savedUser);
        } catch (Exception e) {
            log.error("Error updating user profile: {}", e.getMessage());
            throw new DatabaseOperationException("Error saving user: " + userId);
        }
        return generateUserProfileResponse(updatedUser);
    }

    @Override
    public UserProfileResponse getUserProfile(String userId) {
        return generateUserProfileResponse(fetchUserFromDBUsingUserId(userId));
    }

    @Override
    public List<UserResponse> getAllUsersWithId(List<String> userIds) {
        return usersRepository.findAllById(userIds).stream().map(this::generateUserResponse).toList();
    }


    private Users fetchUserFromDBUsingUserId(String userId) throws UserNotFoundException {
        final Users user;
        try {
            user = usersRepository.findById(userId).orElseThrow();
        } catch (NoSuchElementException e) {
            log.error("No user find with this user-id: {}", userId);
            throw new UserNotFoundException("No user found with user-id: " + userId);
        }
        return user;
    }

    private UserProfileResponse generateUserProfileResponse(Users users) {
        return new UserProfileResponse(
                users.getUsername(),
                users.getEmail(),
                users.getUserProfile().getFirstName(),
                users.getUserProfile().getLastName(),
                users.getUserProfile().getDateOfBirth().toString(),
                users.getUserProfile().getGender().name(),
                users.getJoinedOn().toString()
        );
    }

    private UserResponse generateUserResponse(Users users) {
        return new UserResponse(users.getRealUsername(), users.getJoinedOn());
    }


}
