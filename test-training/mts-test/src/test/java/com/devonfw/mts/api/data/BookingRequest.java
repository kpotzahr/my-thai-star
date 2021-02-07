package com.devonfw.mts.api.data;

import java.time.Instant;

public class BookingRequest {
    public static BookingRequest defaultBookingData() {
        BookingRequest bookingData = new BookingRequest();
        bookingData.setAssistants(2);
        bookingData.setName("test guest");
        bookingData.setBookingDate(Instant.now().plusSeconds(7 * 60 * 60 * 24));
        bookingData.setEmail("test@guest.com");
        return bookingData;
    }

    private String name;
    private Instant bookingDate;
    private String email;
    private Integer assistants;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Instant bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAssistants() {
        return assistants;
    }

    public void setAssistants(Integer assistants) {
        this.assistants = assistants;
    }
}
