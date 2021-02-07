package com.devonfw.mts.api;

import com.devonfw.mts.api.config.LoggedInRequestSetup;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(LoggedInRequestSetup.class)
public class BookingManagementCreateApiTest {
    private static final String BOOKING_BASE_PATH = "/api/services/rest/bookingmanagement/v1";
    private static final String BOOKING_CREATE_PATH = BOOKING_BASE_PATH + "/booking/";
    private static final String BOOKING_SEARCH_PATH = BOOKING_BASE_PATH + "/booking/search";


    private RequestSpecification given;

    @BeforeEach
    public void setup(RequestSpecification given) {
        this.given = given;
    }

    @Test
    public void createSuccessfulBookingAndCheckReturnedData() {

    }



}
