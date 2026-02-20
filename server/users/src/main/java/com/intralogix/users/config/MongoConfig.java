package com.intralogix.users.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;


@Configuration
public class MongoConfig {


    @Bean
    ReactiveMongoTemplate reactiveMongoTemplate(Environment environment){
        String username = "dev";
        String password = "password";
        String database = "users";
        String host = "localhost";
        String authSource = "admin";
        int port = 27017;

        String connectionString = String.format("mongodb://%s:%s@%s:%d/%s?authSource=%s",
                username,
                password,
                host,
                port,
                database,
                authSource
        );
        return new ReactiveMongoTemplate(ReactiveMongoDatabaseFactory.create(
                connectionString
        ));
    }

    @Bean
    MongoMappingContext mappingContext(){
        MongoMappingContext context = new MongoMappingContext();
        context.setAutoIndexCreation(true);
        return context;
    }

}
