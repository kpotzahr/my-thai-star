package com.devonfw.mts.shared;

/**
 * Provides access to data that are guaranteed to be available in the database when a test starts.
 * The implementation is very simple. It only provides constant values. Possible extensions;:
 * - read values from a config file; possibly per test environment
 * - read values from database
 */
public class TestDataPoolContent {
    private static final String DEFAULT_USERNAME = "waiter";
    private static final String DEFAULT_PASSWORD = "waiter";
    private static final String KNOWN_UNIQUE_EMAIL = "user0@mail.com";
    private static final String KNOWN_USER_FOR_EMAIL = "user0";

    private static final TestDataPoolContent INSTANCE = new TestDataPoolContent();

    public static TestDataPoolContent instance() {
        return INSTANCE;
    }

    public String getDefaultUsername() {
        return DEFAULT_USERNAME;
    }

    public String getDefaultPassword() {
        return DEFAULT_PASSWORD;
    }

    public String getUniqueBookingEmail() {
        return KNOWN_UNIQUE_EMAIL;
    }

    public  String getUniqueBookingUser() {
        return KNOWN_USER_FOR_EMAIL;
    }
}
