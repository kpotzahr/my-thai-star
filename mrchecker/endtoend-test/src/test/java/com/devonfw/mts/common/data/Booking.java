package com.devonfw.mts.common.data;

public class Booking {
    public static Booking defaultValidBooking() {
        Booking booking = new Booking();
        booking.setBooking(BookingData.defaultBookingData());
        return booking;
    }

    private BookingData booking;

    public BookingData getBooking() {
        return booking;
    }

    public void setBooking(BookingData booking) {
        this.booking = booking;
    }
}
