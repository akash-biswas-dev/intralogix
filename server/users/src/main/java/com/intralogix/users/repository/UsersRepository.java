package com.intralogix.users.repository;

import com.intralogix.users.models.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import java.util.Optional;

public interface UsersRepository extends ReactiveMongoRepository<Users, String> {

   Optional<Users> findByEmailOrUsernameIgnoreCase(String email, String username);
}
