package com.devonfw.mts.cucumber.data;

import java.util.ArrayList;
import java.util.List;

public class CukesSearchCriteria {

    public Pageable pageable = new Pageable();
    public String email;

    public static CukesSearchCriteria criteria() {
        return new CukesSearchCriteria();
    }

    public CukesSearchCriteria withEmail(String email) {
        this.email = email;
        return this;
    }

    private static class Pageable {
        public int pageSize = 8;
        public int pageNumber = 0;
        public List<String> sort = new ArrayList<>();
    }
}
