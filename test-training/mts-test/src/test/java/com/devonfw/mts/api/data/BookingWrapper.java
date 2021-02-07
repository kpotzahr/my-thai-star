package com.devonfw.mts.api.data;

public class BookingWrapper {
    public static BookingWrapper defaultValidBooking() {
        BookingWrapper booking = new BookingWrapper();
        booking.setBooking(BookingRequest.defaultBookingData());
        return booking;
    }

    private BookingRequest booking;

    public BookingRequest getBooking() {
        return booking;
    }

    public void setBooking(BookingRequest booking) {
        this.booking = booking;
    }
}
