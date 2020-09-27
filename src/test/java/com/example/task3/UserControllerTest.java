package com.example.task3;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.annotation.MicronautTest;
import io.reactivex.Flowable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import spock.lang.Specification;

import javax.inject.Inject;

@MicronautTest
class UserControllerTest extends Specification {

    @Inject
    @Client("/")
    RxHttpClient client;

    @Test
    void shouldGetAllUsers() {
//        given:
        HttpRequest<Publisher<User>> request = HttpRequest.GET("/users");
//        when:
        Flowable<String> users = client.retrieve(request);
//        then:
        Assertions.assertNotNull(users);
    }

    @Test
    void shouldGetUser() {
//        given:
        HttpRequest<Publisher<User>> request = HttpRequest.GET("/users/Parker");
//        when:
        Flowable<String> user = client.retrieve(request);
//        then:
        Assertions.assertNotNull(user);
    }

    @Test
    void shouldGetAllUsersByDefaultPageSize() {
    }

    @Test
    void getAllUsersByPageSize() {
    }

    @Test
    void getAllUsersByDefaultSortOrder() {
    }

    @Test
    void getAllUsersBySortOrder() {
    }

    @Test
    void getAllUsersByDefaultPageNumber() {
    }

    @Test
    void getAllUsersByPageNumber() {
    }

    @Test
    void shouldAddUser() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }
}