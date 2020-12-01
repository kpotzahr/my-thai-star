package com.devonfw.mts.cucumber.config;

import com.devonfw.mts.cucumber.api.LoginService;
import com.devonfw.mts.cucumber.data.CukesUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@ComponentScan("com.devonfw.mts.cucumber.*")
public class CucumberConfiguration {

    @Bean
    public RestTemplate restTemplate(
            @Value("${mythaistar.url:http://localhost:8081/}") String appUrl) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(
                new DefaultUriBuilderFactory(appUrl));
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);
        ResponseEntity<Void> response = restTemplate.postForEntity(
                LoginService.API_LOGIN_URL, CukesUser.validUser(), Void.class);
        if (null != response.getHeaders().get(HttpHeaders.AUTHORIZATION)
                && !response.getHeaders().get(HttpHeaders.AUTHORIZATION).isEmpty()) {
            String token = response.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            restTemplate.setInterceptors(Collections.singletonList(new AuthorizationInterceptor(token)));
        }

        return restTemplate;
    }

    private static class AuthorizationInterceptor implements ClientHttpRequestInterceptor {
        private final String token;

        AuthorizationInterceptor(String token) {
            this.token = token;
        }

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            request.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            request.getHeaders().add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            return execution.execute(request, body);
        }
    }
}
