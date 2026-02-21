package com.intralogix.users.repository.impl;

import com.intralogix.users.models.Users;
import com.intralogix.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;


@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UsersRepository {

    private final ReactiveMongoTemplate mongoTemplate;


    @Override
    public Mono<Users> saveUser(Users users) {
        return mongoTemplate.insert(users);
    }

    @Override
    public Mono<Users> findByEmailOrUsername(String emailOrUsername) {
        Query query = new Query(
                new Criteria().orOperator(
                        Criteria.where("email").is(emailOrUsername),
                        Criteria.where("username").is(emailOrUsername)
                )
        );
        return mongoTemplate.findOne(query,Users.class);
    }

    @Override
    public Mono<Users> findById(String userId) {
        return mongoTemplate.findById(userId, Users.class);
    }
}
