package com.devonfw.mts.api.data;

import java.util.ArrayList;
import java.util.List;

public class SearchCriteria {

    public Pageable pageable = new Pageable();
    public String email;

    public SearchCriteria withEmail(String email) {
        this.email = email;
        return this;
    }

    private class Pageable {
        public int pageSize = 8;
        public int pageNumber = 0;
        public List<String> sort = new ArrayList<>();
    }
}
