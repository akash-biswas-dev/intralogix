package com.intralogix.users.services;

import com.intralogix.users.models.Role;
import com.intralogix.users.models.Users;
import com.intralogix.users.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@DataMongoTest
class UserServiceTest {

    @Autowired
    private UsersRepository usersRepository;


    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);



    @Test
    void saveNewUser() {
        Users user = Users.builder()
                .email("test@mail.com")
                .username("test")
                .password(passwordEncoder.encode("test"))
                .isAccountNonLocked(true)
                .isAccountNonExpired(true)
                .isAccountEnabled(false)
                .dateOfBirth(LocalDate.parse("1999-11-27"))
                .role(Role.USER)
                .joinedOn(LocalDate.now())
                .build();
        Users savedUser = this.usersRepository.save(user);

        savedUser = usersRepository.findById(savedUser.getId()).orElseThrow();

        assertEquals(savedUser.getUsername(), user.getUsername());
        assertTrue(passwordEncoder.matches("test", savedUser.getPassword()));
        assertEquals(savedUser.isAccountNonLocked(), user.isAccountNonLocked());
        assertEquals(savedUser.isAccountNonExpired(), user.isAccountNonExpired());
        assertEquals(savedUser.isEnabled(), user.isEnabled());
        assertEquals(savedUser.getJoinedOn(), user.getJoinedOn());
        assertEquals(savedUser.getRole(), user.getRole());
        assertEquals(savedUser.getEmail(), user.getEmail());
    }

}