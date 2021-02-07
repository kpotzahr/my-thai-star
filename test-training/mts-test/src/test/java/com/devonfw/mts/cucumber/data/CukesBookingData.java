package com.devonfw.mts.cucumber.data;

import java.time.Instant;

public class CukesBookingData {
    public static final String DEFAULT_NAME = "Someone";
    public static final String DEFAULT_EMAIL = "email@test.de";
    public static final int DEFAULT_GUESTS = 2;

    public static CukesBookingData defaultBookingData() {
        CukesBookingData bookingData = new CukesBookingData();
        bookingData.setAssistants(DEFAULT_GUESTS);
        bookingData.setName(DEFAULT_NAME);
        bookingData.setBookingDate(Instant.now().plusSeconds(7 * 60 * 60 * 24));
        bookingData.setEmail(DEFAULT_EMAIL);
        return bookingData;
    }

    private String id;
    private String name;
    private Instant bookingDate;
    private String email;
    private Integer assistants;

    public CukesBookingData() {
    }

    public CukesBookingData(Instant bookingDate, String email, String id) {
        this.bookingDate = bookingDate;
        this.email = email;
        this.id = id;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
