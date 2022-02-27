package com.example.cbrcurrency.util;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.Calendar;
@Slf4j
public class Util {
    public static Calendar getCalendar(LocalDate localDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(localDate.getYear(), localDate.getMonthValue() - 1, localDate.getDayOfMonth());
        log.info(calendar.toString());
        return calendar;
    }
}
