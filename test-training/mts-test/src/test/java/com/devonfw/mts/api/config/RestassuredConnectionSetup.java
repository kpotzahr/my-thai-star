package com.devonfw.mts.api.config;

import com.devonfw.mts.shared.TestConfiguration;
import io.restassured.RestAssured;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class RestassuredConnectionSetup implements BeforeAllCallback {

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        RestAssured.baseURI = TestConfiguration.getApiUrl();
    }

}