package com.devonfw.mts.cucumber.data;

public class CreateBookingResponse {
    private CukesBookingResponse data;
    private int httpStatus;

    public CukesBookingResponse getData() {
        return data;
    }

    public void setData(CukesBookingResponse data) {
        this.data = data;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }
}
