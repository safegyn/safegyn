package org.safegyn.util;

import org.safegyn.model.error.ApiException;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class DateUtil {
    private static final String DATE_TIME_FORMAT = "MM/dd/yyyy HH:mm:ss";

    public static void checkDateFilters(ZonedDateTime fromDate, ZonedDateTime toDate) throws ApiException {
        if (Objects.nonNull(fromDate))
            if (fromDate.isAfter(ZonedDateTime.now()))
                throw new ApiException(
                        ApiException.Type.USER_ERROR,
                        "Checking Date Filters",
                        "\"From\" Date is after current Date",
                        "Specify a from-date before current Date"
                );

        if (Objects.nonNull(toDate))
            if (toDate.isAfter(ZonedDateTime.now()))
                throw new ApiException(
                        ApiException.Type.USER_ERROR,
                        "Checking Date Filters",
                        "\"To\" Date is after current Date",
                        "Specify a to-date before current Date"
                );

        if (Objects.nonNull(fromDate) && Objects.nonNull(toDate))
            if (fromDate.isAfter(toDate))
                throw new ApiException(
                        ApiException.Type.USER_ERROR,
                        "Checking Date Filters",
                        "\"From\" Date lies after \"To\" Date",
                        "Specify a from-date before the to-date"
                );
    }

    public static String getDateTimeString(ZonedDateTime zonedDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        return zonedDateTime.format(formatter);
    }

}