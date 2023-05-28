package com.khesam.certificate.exporter.helper;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

public class DateHelper {

    private static final int[] SUM_DAYS_OF_GREGORIAN_MONTHS = {0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334};

    private DateHelper() {}

    public static long getDifferenceInDays(Date first, Date second) {
        long diff = first.getTime() - second.getTime();
        return Math.abs(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
    }

    public static PersianCalendar gregorianToSolarHijri(Date gregorianDate) {
        GregorianCalendar _gregorianDate = new GregorianCalendar();
        _gregorianDate.setTime(gregorianDate);
        int gy = _gregorianDate.get(Calendar.YEAR);
        int gm = _gregorianDate.get(Calendar.MONTH) + 1;
        int gd = _gregorianDate.get(Calendar.DAY_OF_MONTH);

        int gy2 = (gm > 2) ? (gy + 1) : gy;
        int days = 355666 + (365 * gy) + ((gy2 + 3) / 4) - ((gy2 + 99) / 100)
                + ((gy2 + 399) / 400) + gd + SUM_DAYS_OF_GREGORIAN_MONTHS[gm - 1];
        int jy = -1595 + (33 * (days / 12053));
        days %= 12053;
        jy += 4 * (days / 1461);
        days %= 1461;
        if (days > 365) {
            jy += (days - 1) / 365;
            days = (days - 1) % 365;
        }
        int jm = (days < 186) ? 1 + (days / 31) : 7 + ((days - 186) / 30);
        int jd = 1 + ((days < 186) ? (days % 31) : ((days - 186) % 30));
        return PersianCalendar.getInstance(
                jy, jm, jd,
                _gregorianDate.get(Calendar.HOUR_OF_DAY),
                _gregorianDate.get(Calendar.MINUTE),
                _gregorianDate.get(Calendar.SECOND)
        );
    }


}
