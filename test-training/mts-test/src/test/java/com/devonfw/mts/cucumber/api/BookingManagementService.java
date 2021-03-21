package com.devonfw.mts.cucumber.api;

import com.devonfw.mts.cucumber.data.CukesBooking;
import com.devonfw.mts.cucumber.data.CukesBookingData;
import com.devonfw.mts.cucumber.data.CukesSearchCriteria;
import com.devonfw.mts.shared.TestConfiguration;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static org.assertj.core.api.Assertions.assertThat;

@Component
public class BookingManagementService {
    private static final String BOOKING_BASE_PATH = TestConfiguration.getApiPath() + "/services/rest/bookingmanagement/v1";
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

    public void createBooking(CukesBookingData bookingData) {
        CukesBooking booking = new CukesBooking();
        booking.setBooking(bookingData);
        createBooking(booking);
    }

    private void createBooking(CukesBooking booking) {
        Response response = requestBuilder.request()
                .body(booking).post(BOOKING_CREATE_PATH);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
