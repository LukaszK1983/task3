package com.example.task3;

import com.mongodb.BasicDBObject;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.reactivestreams.client.FindPublisher;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.reactivex.Flowable;
import org.bson.conversions.Bson;
import org.reactivestreams.Publisher;

import javax.validation.Valid;

@Controller("/users")
public class UserController {

    private final MongoClient mongoClient;

    public UserController(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Get
    @Produces(MediaType.APPLICATION_JSON)
    public Flowable<User> getAllUsers() {
        FindPublisher<User> userFindPublisher = getCollection().find().sort(new BasicDBObject("username", 1));
        return Flowable.fromPublisher(userFindPublisher)
                .map(this::starsPassword)
                .limit(20);
    }

    @Get("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Publisher<User> getUser(String username) {
        Bson filter = Filters.eq("username", username);
        Publisher<User> userFindPublisher = getCollection().find(filter).first();
        return Flowable.fromPublisher(userFindPublisher)
                .map(this::starsPassword);
    }

    @Get("/pageSize/")
    @Produces(MediaType.APPLICATION_JSON)
    public Publisher<User> getAllUsersByDefaultPageSize() {
        FindPublisher<User> userFindPublisher = getCollection().find().sort(new BasicDBObject("username", 1));
        return Flowable.fromPublisher(userFindPublisher)
                .map(this::starsPassword)
                .limit(20);
    }

    @Get("/pageSize/{limit}")
    @Produces(MediaType.APPLICATION_JSON)
    public Publisher<User> getAllUsersByPageSize(int limit) {
        FindPublisher<User> userFindPublisher = getCollection().find().sort(new BasicDBObject("username", 1));
        return Flowable.fromPublisher(userFindPublisher)
                .map(this::starsPassword)
                .limit(limit);
    }

    @Get("/sortOrder")
    @Produces(MediaType.APPLICATION_JSON)
    public Publisher<User> getAllUsersByDefaultSortOrder() {
        FindPublisher<User> userFindPublisher = getCollection().find().sort(new BasicDBObject("username", 1));
        return Flowable.fromPublisher(userFindPublisher)
                .map(this::starsPassword)
                .limit(20);
    }

    @Get("/sortOrder/{sortOrder}")
    @Produces(MediaType.APPLICATION_JSON)
    public Publisher<User> getAllUsersBySortOrder(String sortOrder) {
        int order = (sortOrder.toLowerCase().equals("desc") ? -1 : 1);
        FindPublisher<User> userFindPublisher = getCollection().find().sort(new BasicDBObject("username", order));
        return Flowable.fromPublisher(userFindPublisher)
                .map(this::starsPassword)
                .limit(20);
    }

    @Get("/pageNumber")
    @Produces(MediaType.APPLICATION_JSON)
    public Publisher<User> getAllUsersByDefaultPageNumber() {
        FindPublisher<User> userFindPublisher = getCollection().find().sort(new BasicDBObject("username", 1));
        return Flowable.fromPublisher(userFindPublisher)
                .map(this::starsPassword)
                .limit(20);
    }

    @Get("/pageNumber/{pageNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public Publisher<User> getAllUsersByPageNumber(int pageNumber) {
        FindPublisher<User> userFindPublisher = getCollection()
                .find()
                .skip(pageNumber * 20 - 20)
                .limit(20)
                .sort(new BasicDBObject("username", 1));
        return Flowable.fromPublisher(userFindPublisher)
                .map(this::starsPassword);
    }

    @Post
    @Produces(MediaType.APPLICATION_JSON)
    public Publisher<InsertOneResult> addUser(@Body @Valid User user) {
        return getCollection().insertOne(user);
    }

    @Put("/update/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Publisher<UpdateResult> updateUser(@Body @Valid User user, String username) {
        Bson filter = Filters.eq("username", username);
        return getCollection().replaceOne(filter, user);
    }

    @Delete("/delete/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Publisher<DeleteResult> deleteUser(String username) {
        Bson filter = Filters.eq("username", username);
        return getCollection().deleteOne(filter);
    }

    private MongoCollection<User> getCollection() {
        return mongoClient
                .getDatabase("task3")
                .getCollection("users", User.class);
    }

    private User starsPassword(User user) {
        String username = user.getUsername();
        int age = user.getAge();
        return new User(username, "*****", age);
    }
}
