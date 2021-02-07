package com.devonfw.mts.api.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public class WiremockSetup implements BeforeAllCallback, AfterAllCallback, BeforeEachCallback {
    private static WireMockServer wireMockServer = null;

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        wireMockServer = new WireMockServer(wireMockConfig().port(8088));
        wireMockServer.start();
        WireMock.configureFor("localhost", 8088);
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        if (null != wireMockServer) {
            wireMockServer.stop();
        }
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        WireMock.stubFor(post(urlEqualTo("/mail"))
                .willReturn(aResponse().withHeader("Content-Type", "application/json")
                        .withStatus(200)));
    }
}
