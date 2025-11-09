package com.intralogix.users.services.impl;

import com.intralogix.common.services.JwtService;
import com.intralogix.users.clients.WorkspaceClient;
import com.intralogix.users.dtos.requests.NewUserRequest;
import com.intralogix.users.dtos.requests.UserCredentials;
import com.intralogix.users.dtos.requests.UserProfileDTO;
import com.intralogix.users.dtos.response.AuthTokens;
import com.intralogix.users.dtos.response.UserProfileResponse;
import com.intralogix.common.response.UserResponse;
import com.intralogix.users.exception.DatabaseOperationException;
import com.intralogix.users.exception.UserNotFoundException;
import com.intralogix.users.models.Role;
import com.intralogix.users.models.UserProfile;
import com.intralogix.users.models.Users;
import com.intralogix.users.repository.UsersRepository;
import com.intralogix.users.services.AuthService;
import com.intralogix.users.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements AuthService, UserService {

    private final UsersRepository usersRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final WorkspaceClient workspaceClient;

    @Override
    public UserResponse saveUser(NewUserRequest userRequest) {
        Users newUser = Users.builder()
                .email(userRequest.email())
                .username(userRequest.username())
                .password(passwordEncoder.encode(userRequest.password()))
                .dateOfBirth(LocalDate.parse(userRequest.dateOfBirth()))
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
    public UserProfileResponse updateUserDetails(String userId, UserProfileDTO userProfileDetails) {
        final Users savedUser = fetchUserFromDBUsingUserId(userId);

        UserProfile newUserProfile = UserProfile.builder()
                .firstName(userProfileDetails.firstName())
                .lastName(userProfileDetails.lastName())
                .gender(userProfileDetails.gender())
                .build();
        savedUser.setUserProfile(newUserProfile);
        savedUser.setIsAccountEnabled(true);

        final Users updatedUser;
        try{
            updatedUser = usersRepository.save(savedUser);
        }catch (Exception e){
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


    private Users fetchUserFromDBUsingUserId(String userId) throws UserNotFoundException{
        final Users user;
        try {
            user = usersRepository.findById(userId).orElseThrow();
        }catch (NoSuchElementException e){
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
                users.getDateOfBirth().toString(),
                users.getUserProfile().getGender().name(),
                users.getJoinedOn().toString()
        );
    }

    private UserResponse generateUserResponse(Users users) {
        return new UserResponse(users.getRealUsername(), users.getJoinedOn());
    }

    @Override
    public AuthTokens login(UserCredentials userCredentials) {
        final Users users;
        try {
            users = usersRepository
                    .findByEmailOrUsernameIgnoreCase(
                            userCredentials.emailOrUsername(),
                            userCredentials.emailOrUsername()
                    ).orElseThrow();


        }catch (NoSuchElementException ex){
            log.error("Invalid username, no user found with this username or email: {}", userCredentials.emailOrUsername());
            throw new BadCredentialsException("Invalid username or email: " + userCredentials.emailOrUsername());
        }

        if(!passwordEncoder.matches(userCredentials.password(), users.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        String accessToken = jwtService.generateToken(users);
        String  refreshToken = jwtService.generateRefreshToken(users.getId());

        return new  AuthTokens(accessToken, refreshToken);
    }
}
