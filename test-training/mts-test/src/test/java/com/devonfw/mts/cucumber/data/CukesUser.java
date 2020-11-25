package com.devonfw.mts.cucumber.data;

public class CukesUser {
    private static final String FAKEPASSWORD = "fakepassword";
    private static final String DEFAULT_USERNAME = "manager";
    private static final String DEFAULT_PASSWORD = "manager";

    public static CukesUser validUser() {
        return new CukesUser(DEFAULT_USERNAME, DEFAULT_PASSWORD);
    }

    public static CukesUser invalidUser() {
        return new CukesUser("fake", FAKEPASSWORD);
    }


    private final String username;
    private final String password;


    public CukesUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    @Override
    public String toString() {
        return "Usern with username: " + this.username + " and password: " + this.password;
    }
}
