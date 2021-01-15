package com.devonfw.mts.api;

import com.devonfw.mts.api.config.LoggedInRequestSetup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(LoggedInRequestSetup.class)
public class BookingManagementCreateApiTest {
    private static final String BOOKING_BASE_PATH = "/api/services/rest/bookingmanagement/v1";
    private static final String BOOKING_CREATE_PATH = BOOKING_BASE_PATH + "/booking/";


    @Test
    public void createSuccessfulBookingAndCheckReturnedData() {

    }

}
