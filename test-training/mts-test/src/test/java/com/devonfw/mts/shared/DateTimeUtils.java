package com.devonfw.mts.shared;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

public class DateTimeUtils {


    public static String convertDateTimeFormat(String inputFormat, String outputFormat, String value) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(inputFormat);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(outputFormat);

        TemporalAccessor date = inputFormatter.parse(value);
        return outputFormatter.format(date);
    }
}
