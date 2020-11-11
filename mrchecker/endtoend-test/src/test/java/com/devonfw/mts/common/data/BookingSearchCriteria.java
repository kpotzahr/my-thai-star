package com.devonfw.mts.common.data;

import java.util.ArrayList;
import java.util.List;

public class BookingSearchCriteria {
    public Pageable pageable = new Pageable();
    public String email;

    public static BookingSearchCriteria create() {
        return new BookingSearchCriteria();
    }


    public BookingSearchCriteria withEmail(String email) {
        this.email = email;
        return this;
    }

    private class Pageable {
        public int pageSize = 8;
        public int pageNumber = 0;
        public List<String> sort = new ArrayList<>();
    }
}
