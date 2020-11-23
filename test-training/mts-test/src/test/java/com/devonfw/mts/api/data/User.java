package com.devonfw.mts.api.data;

public class User {
    private static final String FAKEPASSWORD = "fakepassword";
    private static final String DEFAULT_USERNAME = "waiter";
    private static final String DEFAULT_PASSWORD = "waiter";

    public static User validUser() {
        return new User(DEFAULT_USERNAME, DEFAULT_PASSWORD);
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
        return "Usern with username: " + this.username + " and password: " + this.password;
    }
}
