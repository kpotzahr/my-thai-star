package com.devonfw.mts.cucumber.data;

import org.springframework.stereotype.Component;

@Component
public class ScenarioVariables {
    private String email;
    private CukesBookingData bookingData;

    public void reset() {
        bookingData = null;
        email = null;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CukesBookingData getBookingData() {
        return bookingData;
    }

    public void setBookingData(CukesBookingData bookingData) {
        this.bookingData = bookingData;
    }
}
