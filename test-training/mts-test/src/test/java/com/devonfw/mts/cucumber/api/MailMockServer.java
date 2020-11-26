package com.devonfw.mts.cucumber.api;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.common.FatalStartupException;
import com.github.tomakehurst.wiremock.matching.RequestPatternBuilder;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@Component
public class MailMockServer {
    private static final Logger LOG = LoggerFactory.getLogger(MailMockServer.class);

    private WireMockServer wireMockServer;
    private final String mailserviceUrl;

    public MailMockServer(@Value("${mailservice.url:http://localhost:8088}") String mailserviceUrl) {
        this.mailserviceUrl = mailserviceUrl;
    }

    @PostConstruct
    public void initialize() throws MalformedURLException {
        URL url = new URL(mailserviceUrl);
        wireMockServer = new WireMockServer(wireMockConfig().port(url.getPort()));
        try {
            wireMockServer.start();
        } catch (FatalStartupException exc) {
            LOG.warn("Wiremock already running");
            LOG.trace("Cannot start Wiremock", exc);

        }
        WireMock.configureFor(url.getHost(), url.getPort());
    }

    public void mockEmailSuccess() {
        WireMock.stubFor(post(urlEqualTo("/mail"))
                .willReturn(aResponse().withHeader("Content-Type", "application/json")
                        .withStatus(400)));
    }

    public void reset() {
        WireMock.reset();
    }

    @PreDestroy
    public void shutdown() {
        if (null != wireMockServer) {
            List<LoggedRequest> requests = wireMockServer.findAll(RequestPatternBuilder.allRequests());
            LOG.info("Requests:\n" + requests);
            wireMockServer.stop();
        }
    }
}
