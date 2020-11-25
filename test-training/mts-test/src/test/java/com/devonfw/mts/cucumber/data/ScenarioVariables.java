package com.devonfw.mts.cucumber.data;

import org.springframework.stereotype.Component;

@Component
public class ScenarioVariables {
    private String email;

    public void reset() {
        email = null;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
