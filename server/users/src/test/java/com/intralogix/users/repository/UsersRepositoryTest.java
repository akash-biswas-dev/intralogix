package com.intralogix.users.repository;

import com.intralogix.users.models.Gender;
import com.intralogix.users.models.Role;
import com.intralogix.users.models.UserProfile;
import com.intralogix.users.models.Users;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


@ActiveProfiles("test")
@DataMongoTest
class UsersRepositoryTest {


    @Autowired
    private UsersRepository usersRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    private Users defaultUser;

    @BeforeEach
    void setUp() {
        this.defaultUser = usersRepository.save(Users.builder()
                .email("test@mail.com")
                .username("test")
                .password(passwordEncoder.encode("test"))
                .isAccountNonLocked(true)
                .isAccountNonExpired(true)
                .isAccountEnabled(false)
                .dateOfBirth(LocalDate.parse("1999-11-27"))
                .role(Role.USER)
                .joinedOn(LocalDate.now())
                .build());
    }

    @AfterEach
    void afterEach() {
        this.usersRepository.deleteById(this.defaultUser.getId());
    }

    @Test
    void saveNewUser() {

        Users savedUser = usersRepository.findById(defaultUser.getId()).orElseThrow();

        assertEquals(savedUser.getUsername(), this.defaultUser.getUsername());
        assertTrue(passwordEncoder.matches("test", this.defaultUser.getPassword()));
        assertEquals(savedUser.isAccountNonLocked(), this.defaultUser.isAccountNonLocked());
        assertEquals(savedUser.isAccountNonExpired(), this.defaultUser.isAccountNonExpired());
        assertEquals(savedUser.isEnabled(), this.defaultUser.isEnabled());
        assertEquals(savedUser.getJoinedOn(), this.defaultUser.getJoinedOn());
        assertEquals(savedUser.getRole(), this.defaultUser.getRole());
        assertEquals(savedUser.getEmail(), this.defaultUser.getEmail());
    }
    @Test
    void findUserByEmailOrUsername(){
        Users savedUser = usersRepository
                .findByEmailOrUsernameIgnoreCase(this.defaultUser.getEmail(), this.defaultUser.getUsername())
                .orElseThrow();

        assertEquals(savedUser.getUsername(), this.defaultUser.getUsername());
        assertTrue(passwordEncoder.matches("test", this.defaultUser.getPassword()));
        assertEquals(savedUser.isAccountNonLocked(), this.defaultUser.isAccountNonLocked());
        assertEquals(savedUser.isAccountNonExpired(), this.defaultUser.isAccountNonExpired());
        assertEquals(savedUser.isEnabled(), this.defaultUser.isEnabled());
        assertEquals(savedUser.getJoinedOn(), this.defaultUser.getJoinedOn());
        assertEquals(savedUser.getRole(), this.defaultUser.getRole());
        assertEquals(savedUser.getEmail(), this.defaultUser.getEmail());
    }

    @Test
    void shouldUpdateUserProfile() {
        Users savedUser = this.usersRepository.findById(this.defaultUser.getId()).orElseThrow();
        UserProfile newUserProfile = UserProfile.builder()
                .firstName("test")
                .lastName("password")
                .gender(Gender.MALE)
                .build();
        savedUser.setUserProfile(newUserProfile);

        Users updatedUser = usersRepository.save(savedUser);
        assertEquals(updatedUser.getUserProfile().getFirstName(), newUserProfile.getFirstName());
        assertEquals(updatedUser.getUserProfile().getLastName(), newUserProfile.getLastName());
        assertEquals(updatedUser.getUserProfile().getGender(), newUserProfile.getGender());
    }

}