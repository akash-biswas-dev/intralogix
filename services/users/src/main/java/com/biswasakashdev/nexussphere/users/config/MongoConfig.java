package com.biswasakashdev.nexussphere.users.config;


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
        String connectionString = environment.getProperty("application.db");
        if (connectionString == null || connectionString.trim().isEmpty()) {
            throw new RuntimeException("Database connection string is empty");
        }

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
