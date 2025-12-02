package com.intralogix.users.services;

import com.intralogix.users.dtos.requests.UserProfileRequest;
import com.intralogix.users.dtos.response.UserProfileResponse;
import com.intralogix.users.models.Gender;
import com.intralogix.users.models.Role;
import com.intralogix.users.models.UserProfile;
import com.intralogix.users.models.Users;
import com.intralogix.users.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {


    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockitoBean
    private UsersRepository usersRepository;

    private Users defaultUser;

    @BeforeEach
    void setUp() {
        this.defaultUser = Users.builder()
                .id("mongo-id")
                .email("test@mail.com")
                .username("test")
                .password(passwordEncoder.encode("test"))
                .isAccountNonLocked(true)
                .isAccountNonExpired(true)
                .isAccountEnabled(false)
                .role(Role.USER)
                .joinedOn(LocalDate.now())
                .build();
    }

    @Test
    void shouldUpdateUserDetails() {

        UserProfileRequest newUserProfile = new UserProfileRequest("firstName", "lastName", Gender.MALE);

        when(usersRepository.findById(this.defaultUser.getId())).thenReturn(Optional.of(this.defaultUser));

        Users savedUser = this.defaultUser;
        savedUser.setUserProfile(UserProfile.builder()
                .gender(newUserProfile.gender())
                .firstName(newUserProfile.firstName())
                .lastName(newUserProfile.lastName())
                .build());

        when(usersRepository.save(savedUser)).thenReturn(savedUser);

        UserProfileResponse userProfileResponse = userService.updateUserDetails(this.defaultUser.getId(), newUserProfile);

        assertEquals(userProfileResponse.firstName(), newUserProfile.firstName());
        assertEquals(userProfileResponse.lastName(), newUserProfile.lastName());
        assertEquals(userProfileResponse.gender(), newUserProfile.gender().name());
        assertEquals(userProfileResponse.email(), this.defaultUser.getEmail());
        assertEquals(userProfileResponse.username(), this.defaultUser.getUsername());
        assertEquals(userProfileResponse.joinedOn(), this.defaultUser.getJoinedOn().toString());

    }

}