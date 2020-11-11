package com.devonfw.mts.cucumber.api;

import com.devonfw.mts.common.data.Booking;
import com.devonfw.mts.common.data.BookingSearchCriteria;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookingManagementService {
    private static final String BOOKING_BASE_PATH = "/api/services/rest/bookingmanagement/v1";

    @Autowired
    MyThaiStarRestRequestBuilder requestBuilder;

    public boolean hasBookingForEmail(String email) {
        Response searchResponse = requestBuilder.request()
                .body(BookingSearchCriteria.create().withEmail(email)).post(BOOKING_BASE_PATH + "/booking/search");
        return searchResponse.getBody().print().contains(email);
    }

    public void createBookingForEmail(String email) {
        Booking booking = Booking.defaultValidBooking();
        booking.getBooking().setEmail(email);
        sendValidBookingRequest(booking);
    }

    private void sendValidBookingRequest(Booking booking) {
        requestBuilder.request()
                .body(booking).post(BOOKING_BASE_PATH + "/booking/")
                .then().assertThat().statusCode(200);
    }
}
