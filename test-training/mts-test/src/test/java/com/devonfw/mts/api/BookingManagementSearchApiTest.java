package com.devonfw.mts.api;

import com.devonfw.mts.api.config.LoggedInRequestSetup;


import com.devonfw.mts.api.data.SearchCriteria;
import com.devonfw.mts.shared.TestDataPoolContent;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@ExtendWith(LoggedInRequestSetup.class)
public class BookingManagementSearchApiTest {
    private static final String BOOKING_BASE_PATH = "/api/services/rest/bookingmanagement/v1";
    private static final String BOOKING_SEARCH_PATH = BOOKING_BASE_PATH + "/booking/search";


    @Test
    public void searchByUnknownEmail(RequestSpecification given) {
        given.body(new SearchCriteria().withEmail("unknown@Invalid,com")).
                when().post(BOOKING_SEARCH_PATH).
                then().statusCode(204);
    }

    @Test
    public void searchByKnownEmail(RequestSpecification given) {
        String bookingEmail = TestDataPoolContent.instance().getUniqueBookingEmail();
        given.body(new SearchCriteria().withEmail(bookingEmail)).
                when().post(BOOKING_SEARCH_PATH).
                then().statusCode(200).
                body("content", hasSize(1)).
                body("content[0].booking.email", equalTo(bookingEmail)).
                body("content[0].booking.name", equalTo(TestDataPoolContent.instance().getUniqueBookingUser()));
    }

}
