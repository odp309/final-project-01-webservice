package com.bni.finalproject01webservice.utility;

import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.EnumSet;
import java.util.Set;

@Component
public class WorkingDaysCalculator {

    public long countWorkingDays(LocalDateTime startDateTime, LocalDate endDate) {
        Set<DayOfWeek> weekend = EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);

        // Adjust start date if the time is past 3 PM
        if (startDateTime.toLocalTime().isAfter(LocalTime.of(15, 0))) {
            startDateTime = startDateTime.plusDays(1).with(LocalTime.MIN);
        }

        LocalDate startDate = startDateTime.toLocalDate();

        return startDate.datesUntil(endDate.plusDays(1))
                .filter(date -> !weekend.contains(date.getDayOfWeek()) && !IndonesianHolidays.isHoliday(date))
                .count();
    }
}