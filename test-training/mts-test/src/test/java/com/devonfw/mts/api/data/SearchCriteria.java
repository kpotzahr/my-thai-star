package com.devonfw.mts.api.data;

import java.util.ArrayList;
import java.util.List;

public class SearchCriteria {

    public Pageable pageable = new Pageable();
    public String email;
    public String bookingToken;

    public SearchCriteria withEmail(String email) {
        this.email = email;
        return this;
    }

    public SearchCriteria withBookingToken(String token) {
        this.bookingToken = token;
        return this;
    }

    private static class Pageable {
        public int pageSize = 8;
        public int pageNumber = 0;
        public List<String> sort = new ArrayList<>();
    }
}
