package com.devonfw.mts.cucumber.api;

import com.devonfw.mts.common.data.Booking;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookingManagementService {
    private static final String BOOKING_BASE_PATH = "/api/services/rest/bookingmanagement/v1";

    @Autowired
    MyThaiStarRestRequestBuilder requestBuilder;

    public boolean hasBookingForEmail(String email) {
        Response searchResponse = requestBuilder.request()
                .body(new SearchCriteria().withEmail(email)).post(BOOKING_BASE_PATH + "/booking/search");
        return searchResponse.getBody().print().contains(email);
    }

    public void createBookingForEmail(String email) {
        Booking booking = Booking.defaultValidBooking();
        booking.getBooking().setEmail(email);
        requestBuilder.request()
                .body(booking).post(BOOKING_BASE_PATH+ "/booking/")
                .then().assertThat().statusCode(200);
    }

    private class Pageable {
        public int pageSize = 8;
        public int pageNumber = 0;
        public List<String> sort = new ArrayList<>();
    }

    private class SearchCriteria {
        public Pageable pageable = new Pageable();
        public String email;

        public SearchCriteria withEmail(String email) {
            this.email = email;
            return this;
        }
    }
}
