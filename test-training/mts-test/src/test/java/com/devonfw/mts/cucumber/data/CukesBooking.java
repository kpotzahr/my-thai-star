package com.devonfw.mts.cucumber.data;

public class CukesBooking {
    public static CukesBooking defaultValidBooking() {
        CukesBooking booking = new CukesBooking();
        booking.setBooking(CukesBookingData.defaultBookingData());
        return booking;
    }

    private CukesBookingData booking;

    public CukesBookingData getBooking() {
        return booking;
    }

    public void setBooking(CukesBookingData booking) {
        this.booking = booking;
    }
}
