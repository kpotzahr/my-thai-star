package com.devonfw.mts.api;

import com.devonfw.mts.api.config.LoggedInRequestSetup;
import com.devonfw.mts.api.data.BookingRequest;
import com.devonfw.mts.api.data.BookingResponse;
import com.devonfw.mts.api.data.BookingWrapper;
import com.devonfw.mts.api.data.SearchCriteria;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.hasSize;

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
    public void createSuccessfulBookingAndCheckReturnedDataDirect() {
        BookingWrapper bookingWrapper = new BookingWrapper();
        bookingWrapper.setBooking(new BookingRequest());
        bookingWrapper.getBooking().setName("My Thai Guest & Friends");
        bookingWrapper.getBooking().setEmail("test@mail.com");
        bookingWrapper.getBooking().setAssistants(8);
        bookingWrapper.getBooking().setBookingDate(Instant.now().plusSeconds(2 * 60 * 60 * 24));

        ValidatableResponse response = given.body(bookingWrapper)
                .when()
                .post(BOOKING_CREATE_PATH).then();
        response.statusCode(200)
                .body("name", equalTo(bookingWrapper.getBooking().getName()))
                .body("email", equalTo(bookingWrapper.getBooking().getEmail()))
                .body("canceled", is(false))
                .body("assistants", equalTo(bookingWrapper.getBooking().getAssistants()))
                // TODO check booking date
                .body("orderId", nullValue());

        String bookingToken = response.extract().path("bookingToken");

        given.body(new SearchCriteria().withBookingToken(bookingToken)).
                when().post(BOOKING_SEARCH_PATH).
                then().statusCode(200).
                body("content", hasSize(1));
    }

    @Test
    public void createSuccessfulBookingAndCheckReturnedDataWithObjectMapper() {
        BookingWrapper bookingWrapper = new BookingWrapper();
        bookingWrapper.setBooking(new BookingRequest());
        bookingWrapper.getBooking().setName("My Thai Guest & Friends");
        bookingWrapper.getBooking().setEmail("test@mail.com");
        bookingWrapper.getBooking().setAssistants(8);
        bookingWrapper.getBooking().setBookingDate(Instant.now().plusSeconds(2 * 60 * 60 * 24));

        BookingResponse returnedBooking = given.body(bookingWrapper)
                .when()
                .post(BOOKING_CREATE_PATH).as(BookingResponse.class);
        assertThat(returnedBooking).usingRecursiveComparison()
                .ignoringFields("id", "bookingToken", "canceled", "modificationCounter",
                        "comment", "expirationDate", "creationDate", "bookingType", "tableId", "orderId", "userId")
                .isEqualTo(bookingWrapper.getBooking());


        given.body(new SearchCriteria().withBookingToken(returnedBooking.getBookingToken())
                .withEmail(bookingWrapper.getBooking().getEmail())).
                when().post(BOOKING_SEARCH_PATH).
                then().statusCode(200).
                body("content", hasSize(1));
    }
}
