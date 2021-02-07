package com.devonfw.mts.cucumber.api;

import com.devonfw.mts.cucumber.data.CreateBookingResponse;
import com.devonfw.mts.cucumber.data.CukesBooking;
import com.devonfw.mts.cucumber.data.CukesBookingData;
import com.devonfw.mts.cucumber.data.CukesBookingResponse;
import com.devonfw.mts.cucumber.data.CukesSearchCriteria;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookingManagementService {
    private static final String BOOKING_BASE_PATH = "api/services/rest/bookingmanagement/v1";
    private static final String BOOKING_CREATE_PATH = BOOKING_BASE_PATH + "/booking/";
    private static final String BOOKING_SEARCH_PATH = BOOKING_BASE_PATH + "/booking/search";

    @Autowired
    private RestRequestBuilder requestBuilder;


    public boolean hasBookingForEmail(String email) {
        CukesSearchCriteria searchCriteria = CukesSearchCriteria.criteria().withEmail(email);

        Response searchResponse = requestBuilder.request()
                .body(searchCriteria).post(BOOKING_SEARCH_PATH);
        return searchResponse.getBody().print().contains(email);
    }

    public void createBookingForEmail(String email) {
        CukesBooking booking = CukesBooking.defaultValidBooking();
        booking.getBooking().setEmail(email);
        createBooking(booking);
    }

    public CreateBookingResponse createBooking(CukesBookingData bookingData) {
        CukesBooking booking = new CukesBooking();
        booking.setBooking(bookingData);
        return createBooking(booking);
    }

    private CreateBookingResponse createBooking(CukesBooking booking) {
        Response response = requestBuilder.request()
                .body(booking).post(BOOKING_BASE_PATH + "/booking/");
        int statusCode = response.then().assertThat().extract().statusCode();
        CreateBookingResponse createBookingResponse = new CreateBookingResponse();
        createBookingResponse.setHttpStatus(statusCode);
        if (statusCode == 200) {
            createBookingResponse.setData(response.as(CukesBookingResponse.class));
        }
        return createBookingResponse;
    }
}
