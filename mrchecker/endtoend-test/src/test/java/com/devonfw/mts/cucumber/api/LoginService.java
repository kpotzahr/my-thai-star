package com.devonfw.mts.cucumber.api;

import com.devonfw.mts.common.data.User;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.stereotype.Component;

@Component
public class LoginService {

    public String login(User user) {
        User defaultUser = new User("waiter", "waiter");
        Response post = RestAssured.with().body(defaultUser).post("/api/login");
        return post.header("Authorization");
    }
}
