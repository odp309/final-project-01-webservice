package com.bni.finalproject01webservice.utility;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class IndonesianHolidays {
    private static final Set<LocalDate> holidays = new HashSet<>();

    static {
        holidays.add(LocalDate.of(2024, 1, 1)); // New Year's Day
        holidays.add(LocalDate.of(2024, 3, 22)); // Nyepi (Balinese Day of Silence)
        holidays.add(LocalDate.of(2024, 4, 10)); // Idul Fitri
        holidays.add(LocalDate.of(2024, 4, 11)); // Idul Fitri Holiday
        holidays.add(LocalDate.of(2024, 5, 1)); // Labor Day
        // Add more holidays as needed
    }

    public static boolean isHoliday(LocalDate date) {
        return holidays.contains(date);
    }
}