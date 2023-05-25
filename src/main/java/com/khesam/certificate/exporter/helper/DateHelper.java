package com.khesam.certificate.exporter.helper;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateHelper {

    private DateHelper() {}

    public static long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return Math.abs(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
    }
}
