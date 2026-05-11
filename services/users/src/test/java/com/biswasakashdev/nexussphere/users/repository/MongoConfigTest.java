package com.biswasakashdev.nexussphere.users.repository;


import com.biswasakashdev.nexussphere.users.models.Users;
import com.biswasakashdev.nexussphere.users.repository.impl.UserRepositoryImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.mongodb.test.autoconfigure.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

@DataMongoTest
@ActiveProfiles("test")
@Import(value = {
        UserRepositoryImpl.class
})
public class MongoConfigTest {

    @Autowired
    public UsersRepository repository;

    @Autowired
    public ReactiveMongoTemplate mongoTemplate;



    @Test
    public void shouldNotCreateAnotherUserWithSameEmail() {
        String email = "abc@email.com";
        Users user1 = Users
                .builder()
                .email(email)
                .build();
        Users user2 = Users
                .builder()
                .email(email)
                .build();

        repository.saveUser(user1)
                .then(repository.saveUser(user2))
                .as(StepVerifier::create)
                .expectError(DuplicateKeyException.class)
                .verify();
    }


    @AfterEach
    void afterEach(){
        mongoTemplate.dropCollection("users").block();
    }

}
