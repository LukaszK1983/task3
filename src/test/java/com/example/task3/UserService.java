package com.example.task3;

import com.mongodb.client.result.InsertOneResult;
import org.reactivestreams.Publisher;

interface UserService {
    Publisher<InsertOneResult> addUser(User user);
}