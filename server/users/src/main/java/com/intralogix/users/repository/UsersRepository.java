package com.intralogix.users.repository;

import com.intralogix.users.models.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class UsersRepository {

   private final ReactiveMongoTemplate mongoTemplate;


   public Mono<Users> saveUser(Users users){
      return mongoTemplate.insert(users,"users");
   }

}
