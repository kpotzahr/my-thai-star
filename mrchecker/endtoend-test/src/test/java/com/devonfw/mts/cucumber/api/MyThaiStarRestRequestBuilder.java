package com.devonfw.mts.cucumber.api;

import com.devonfw.mts.common.data.User;
import com.devonfw.mts.common.readProperties.ConfigFileReader;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MyThaiStarRestRequestBuilder {
    @Autowired
    private LoginService loginService;

    private String accessToken = null;

    public RequestSpecification request() {
        return RestAssured
                .with().contentType("application/json").header("Authorization", "Bearer " + accessToken);
    }

    @PostConstruct
    private void initialize() {
        RestAssured.baseURI = new ConfigFileReader().getProperty("mythaistar.url");
        accessToken = loginService.login(User.defaultUser());
    }

}
