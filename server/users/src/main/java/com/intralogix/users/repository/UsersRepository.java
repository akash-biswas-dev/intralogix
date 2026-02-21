package com.intralogix.users.repository;

import com.intralogix.users.models.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Optional;


public interface UsersRepository {

    Mono<Users> saveUser(Users users);

    Mono<Users> findByEmailOrUsername(String emailOrUsername);

    Mono<Users> findById(String userId);
}
