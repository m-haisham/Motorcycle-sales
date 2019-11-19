package com.cerberus.models.date;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.IntStream;

public class DateHelper {

    public static int[] getMonths() {
        int[] months = new int[12];
        for (int i = 0; i < months.length; i++) {
            months[i] = i + 1;
        }

        return months;
    }

    public static int[] getDays(int month) {
        int[] days;

        int[] days31 = new int[] {1, 3, 5, 7, 8, 10, 12};

        if (IntStream.of(days31).anyMatch(x -> x == month)) {
            days = new int[31];
        } else if (month == 2) {

            LocalDate today = LocalDate.now();

            if (today.isLeapYear()) {
                days = new int[29];
            } else {
                days = new int[28];
            }

        } else {
            days = new int[30];
        }

        // populate
        for (int i = 0; i < days.length; i++) {
            days[i] = i + 1;
        }

        return days;
    }

    private DateHelper() {}
}
