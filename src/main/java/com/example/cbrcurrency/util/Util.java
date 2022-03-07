package com.example.cbrcurrency.util;

import lombok.extern.slf4j.Slf4j;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

@Slf4j
public final class Util {
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private Util() {
    }

    public static Calendar getCalendar(LocalDate localDate) {
        Date date = Date.valueOf(localDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setTimeInMillis(date.getTime());

        log.info(String.format("localdate : %1s ---> calendar : %2s", localDate.toString(), calendar.toString()));

        return calendar;
    }
}
