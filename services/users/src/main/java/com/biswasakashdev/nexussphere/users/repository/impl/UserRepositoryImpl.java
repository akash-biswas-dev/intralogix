package com.biswasakashdev.nexussphere.users.repository.impl;

import com.biswasakashdev.nexussphere.users.models.Users;
import com.biswasakashdev.nexussphere.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.beans.PropertyDescriptor;


@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UsersRepository {

    private final ReactiveMongoTemplate mongoTemplate;


    @Override
    public Mono<Users> saveUser(Users users) {
        return mongoTemplate.insert(users);
    }

    @Override
    public Mono<Boolean> isProfileCompleted(String userId) {
        Query query = new Query(Criteria.where("id").is(userId));
        query
                .fields()
                .include("profileCompleted")
                .exclude("_id");

        return mongoTemplate
                .findOne(query, Users.class)
                .map(Users::getProfileCompleted);
    }

    @Override
    public Mono<Users> findByEmailOrUsername(String emailOrUsername) {
        Query query = new Query(
                new Criteria().orOperator(
                        Criteria.where("email").is(emailOrUsername),
                        Criteria.where("username").is(emailOrUsername)
                )
        );
        return mongoTemplate.findOne(query, Users.class);
    }

    @Override
    public Mono<Users> findById(String userId) {
        return mongoTemplate.findById(userId, Users.class);
    }

    @Override
    public Mono<Boolean> isUserExists(String userId) {
        return mongoTemplate.exists(Query.query(Criteria.where("id").is(userId)), Users.class);
    }

    @Override
    public Mono<Users> updateUser(String userId, UserUpdates updates, boolean ignoreNull) {
        Update userUpdates = getUpdates(null, updates, ignoreNull);

        Query query = new Query(Criteria.where("id").is(userId));
        return mongoTemplate
                .findAndModify(
                        query,
                        userUpdates,
                        FindAndModifyOptions.options().returnNew(true),
                        Users.class
                );
    }


    @Override
    public Mono<Users> updateProfile(String id, UserProfileUpdates updates, boolean ignoreNull) {
        Update profileUpdates = getUpdates("userProfile", updates, ignoreNull);

        return mongoTemplate
                .findAndModify(
                        new Query(Criteria.where("id").is(id)),
                        profileUpdates,
                        FindAndModifyOptions.options().returnNew(true),
                        Users.class
                );
    }

    private static <T> Update getUpdates(String parent, T updates, boolean ignoreNull) {
        BeanWrapper profileUpdates = new BeanWrapperImpl(updates);
        Update update = new Update();

        for (PropertyDescriptor fields : profileUpdates.getPropertyDescriptors()) {
            String fieldName = fields.getName();
            if ("class".equals(fieldName)) continue;

            Object fieldValue = profileUpdates.getPropertyValue(fieldName);
            if (ignoreNull && fieldValue == null) continue;
            String dbFieldName = parent != null ? parent + "." + fieldName : fieldName;

            update.set(dbFieldName, fieldValue);
        }
        return update;
    }


}
