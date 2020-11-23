package com.devonfw.mts.api;

import com.devonfw.mts.api.config.LoggedInRequestSetup;
import com.devonfw.mts.api.data.SearchCriteria;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@ExtendWith(LoggedInRequestSetup.class)
public class BookingManagementSearchApiTest {
    private static final String BOOKING_BASE_PATH = "/api/services/rest/bookingmanagement/v1";
    private static final String BOOKING_SEARCH_PATH = BOOKING_BASE_PATH + "/booking/search";
    private static final String KNOWN_UNIQUE_EMAIL = "user0@mail.com";

    @Test
    public void searchByUnknownEmail(RequestSpecification given) {
        given.body(new SearchCriteria().withEmail("unknownAndInvalid")).
                when().post(BOOKING_SEARCH_PATH).
                then().statusCode(204);
    }


    @Test
    public void searchByKnownEmail(RequestSpecification given) {
        given.body(new SearchCriteria().withEmail(KNOWN_UNIQUE_EMAIL)).
                when().post(BOOKING_SEARCH_PATH).
                then().statusCode(200).
                body("content", hasSize(1)).
                body("content[0].booking.email", equalTo(KNOWN_UNIQUE_EMAIL)).
                body("content[0].booking.name", equalTo("user0"));
    }

}
