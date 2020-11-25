package com.devonfw.mts.cucumber.api;

import com.devonfw.mts.cucumber.data.CukesBooking;
import com.devonfw.mts.cucumber.data.CukesSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@Component
public class BookingManagementService {
    private static final String BOOKING_BASE_PATH = "api/services/rest/bookingmanagement/v1";
    private static final String BOOKING_CREATE_PATH = BOOKING_BASE_PATH + "/booking/";
    private static final String BOOKING_SEARCH_PATH = BOOKING_BASE_PATH + "/booking/search";

    @Autowired
    private RestTemplate restTemplate;


    public boolean hasBookingForEmail(String email) {
        CukesSearchCriteria searchCriteria = CukesSearchCriteria.criteria().withEmail(email);

        ResponseEntity<String> response = restTemplate.postForEntity(
                BOOKING_SEARCH_PATH, searchCriteria, String.class);

        return (null != response.getBody()) && response.getBody().contains(email);
    }

    public void createBookingForEmail(String email) {
        CukesBooking booking = CukesBooking.defaultValidBooking();
        booking.getBooking().setEmail(email);
        ResponseEntity<CukesBooking> response = restTemplate.postForEntity(
                BOOKING_CREATE_PATH, booking, CukesBooking.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
