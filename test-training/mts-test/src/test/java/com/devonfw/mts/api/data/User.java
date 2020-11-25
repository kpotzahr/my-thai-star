package com.devonfw.mts.api.data;

import com.devonfw.mts.shared.TestDataPoolContent;

public class User {
    private static final String FAKEPASSWORD = "fakepassword";


    public static User validUser() {
        return new User(TestDataPoolContent.instance().getDefaultUsername(),
                TestDataPoolContent.instance().getDefaultPassword());
    }

    private final String username;
    private final String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username) {
        this.username = username;
        this.password = FAKEPASSWORD;
    }

    @Override
    public String toString() {
        return "User with username: " + this.username + " and password: " + this.password;
    }
}
