package com.devonfw.mts.api.config;

import com.devonfw.mts.api.data.User;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.springframework.http.HttpHeaders;

public class LoggedInRequestSetup implements ParameterResolver {
    private static final String API_LOGIN_URL = "/api/login";

    private static String token = null;

    private String generateLoginToken() {
        RestAssured.baseURI = TestConfiguration.getApiUrl();
        return RestAssured.with().body(User.validUser())
                .post(API_LOGIN_URL).header(HttpHeaders.AUTHORIZATION);
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == RequestSpecification.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        if (null == token) {
            token = generateLoginToken();
        }

        return RestAssured.with().contentType("application/json")
                .header("Authorization", "Bearer " + token);
    }
}
