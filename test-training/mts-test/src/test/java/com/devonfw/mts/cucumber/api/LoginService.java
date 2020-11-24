package com.devonfw.mts.cucumber.api;

import com.devonfw.mts.cucumber.data.CukesUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class LoginService {
    public static final String API_LOGIN_URL = "api/login";

    @Autowired
    private RestTemplate restTemplate;

    public String login(CukesUser user) {
        ResponseEntity<Void> response = restTemplate.postForEntity(
                LoginService.API_LOGIN_URL, user, Void.class);
        return response.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
    }


}
