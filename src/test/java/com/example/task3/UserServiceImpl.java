package com.example.task3;

import com.mongodb.client.result.InsertOneResult;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import org.reactivestreams.Publisher;
import javax.inject.Singleton;

@Singleton
public class UserServiceImpl implements UserService {

    private final MongoClient mongoClient;

    public UserServiceImpl(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Override
    public Publisher<InsertOneResult> addUser(User user) {
        return getCollection().insertOne(user);
    }

    private MongoCollection<User> getCollection() {
        return mongoClient
                .getDatabase("task3")
                .getCollection("users", User.class);
    }
}