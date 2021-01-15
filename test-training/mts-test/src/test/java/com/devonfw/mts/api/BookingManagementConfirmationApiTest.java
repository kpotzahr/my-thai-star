package com.devonfw.mts.api;

import com.devonfw.mts.api.config.LoggedInRequestSetup;
import com.devonfw.mts.api.config.WiremockSetup;
import com.devonfw.mts.cucumber.data.CukesBooking;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.matching.RequestPatternBuilder;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(LoggedInRequestSetup.class)
@ExtendWith(WiremockSetup.class)
public class BookingManagementConfirmationApiTest {
    private static final String BOOKING_BASE_PATH = "/api/services/rest/bookingmanagement/v1";
    private static final String BOOKING_CREATE_PATH = BOOKING_BASE_PATH + "/booking/";

    @Test
    public void createSuccessfulBookingAndCheckConfirmationEmail(RequestSpecification given) {
        CukesBooking booking = CukesBooking.defaultValidBooking();
        given.body(booking)
                .when()
                .post(BOOKING_CREATE_PATH)
                .then().statusCode(200);
        List<LoggedRequest> requests = WireMock.findAll(RequestPatternBuilder.allRequests());
        assertThat(requests).hasSize(1);
        assertThat(new String(requests.get(0).getBody())).contains(booking.getBooking().getEmail());
    }

    @Test
    public void bookingNotSuccessfulForNotWorkingEmail() {

    }

}
