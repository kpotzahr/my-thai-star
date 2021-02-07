package com.devonfw.mts.api;

import com.devonfw.mts.api.config.LoggedInRequestSetup;
import com.devonfw.mts.api.config.WiremockSetup;
import com.devonfw.mts.api.data.BookingWrapper;
import com.devonfw.mts.api.data.SearchCriteria;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(LoggedInRequestSetup.class)
@ExtendWith(WiremockSetup.class)
public class BookingManagementConfirmationApiTest {
    private static final String BOOKING_BASE_PATH = "/api/services/rest/bookingmanagement/v1";
    private static final String BOOKING_CREATE_PATH = BOOKING_BASE_PATH + "/booking/";
    private static final String BOOKING_SEARCH_PATH = BOOKING_BASE_PATH + "/booking/search";

    private RequestSpecification given;

    @BeforeEach
    public void setup(RequestSpecification given) {
        this.given = given;
        WireMock.resetAllRequests();
    }

    @Test
    public void createSuccessfulBookingAndCheckConfirmationEmail() {
        BookingWrapper booking = BookingWrapper.defaultValidBooking();
        given.body(booking)
                .when()
                .post(BOOKING_CREATE_PATH)
                .then().statusCode(200);
        List<LoggedRequest> requests = WireMock.findAll(postRequestedFor(urlEqualTo("/mail")));
        assertThat(requests).hasSize(1);
        assertThat(new String(requests.get(0).getBody())).contains(booking.getBooking().getEmail());
    }

    @Test
    public void bookingNotSuccessfulForNotWorkingEmail() {
        WireMock.stubFor(post(urlEqualTo("/mail"))
                .willReturn(aResponse().withStatus(400)));

        String email = "myveryspecialemail@error.de";
        BookingWrapper booking = BookingWrapper.defaultValidBooking();
        booking.getBooking().setEmail(email);

        given.body(booking)
                .when()
                .post(BOOKING_CREATE_PATH)
                .then().statusCode(500);

        given.body(new SearchCriteria().withEmail(booking.getBooking().getEmail())).
                when().post(BOOKING_SEARCH_PATH).
                then().statusCode(204);
    }

}
