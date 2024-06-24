package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.interfaces.DateTimeInterface;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
public class DateTimeService implements DateTimeInterface {

    @Override
    public Date getCurrentDateTimeInJakarta() {

        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Jakarta"));
        Instant instant = now.toInstant();

        return Date.from(instant);
    }
}
