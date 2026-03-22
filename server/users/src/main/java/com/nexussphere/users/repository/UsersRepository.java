package com.nexussphere.users.repository;

import com.mongodb.client.result.UpdateResult;
import com.nexussphere.users.models.Gender;
import com.nexussphere.users.models.Users;
import com.nexussphere.users.repository.impl.UserRepositoryImpl;
import lombok.Builder;
import reactor.core.publisher.Mono;

import java.time.LocalDate;


public interface UsersRepository {

    Mono<Users> saveUser(Users users);

    Mono<Users> findByEmailOrUsername(String emailOrUsername);

    Mono<Users> findById(String userId);

    Mono<Boolean> isUserExists(String userId);

    @Builder
    record UserUpdates(
            String username,
            String email,
            String password,
            boolean isAccountLocked,
            boolean isAccountEnabled
    ) {

    }

    Mono<Users> updateUser(String userId, UserRepositoryImpl.UserUpdates updates, boolean ignoreNull);

    @Builder
    record UserProfileUpdates(
            String firstName,
            String lastName,
            Gender gender,
            LocalDate dateOfBirth
    ){

    }

    Mono<Users> updateProfile(String userId, UserRepositoryImpl.UserProfileUpdates updates, boolean ignoreNull);
}
