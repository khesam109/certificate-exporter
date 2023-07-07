package com.khesam.certificate.exporter.helper.utils.date;

import com.khesam.certificate.exporter.helper.utils.StringUtils;

import java.util.Arrays;
import java.util.List;

public class PersianCalendar {

    public int year;
    public int month;
    public int day;
    public int hour;
    public int minute;
    public int second;

    private PersianCalendar(int year, int month, int day, int hour, int minute, int second) {
        if (year < 1 || (month < 1 || month > 12) || (day < 1 || day > 31) ||
                (hour < 0 || hour > 23) || (minute < 0 || minute > 59) || (second < 0 || second > 59)) {
            throw new IllegalArgumentException("Invalid date value");
        }

        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public static PersianCalendar getInstance(List<Integer> fields) {
        int year = 1, month = 1, day = 1, minute = 0, hour = 0, second = 0;

        if (fields.size() > 0) year = fields.get(0);
        if (fields.size() > 1) month = fields.get(1);
        if (fields.size() > 2) day = fields.get(2);
        if (fields.size() > 3) hour = fields.get(3);
        if (fields.size() > 4) minute = fields.get(4);
        if (fields.size() > 5) second = fields.get(5);

        return new PersianCalendar(year, month, day, hour, minute, second);
    }

    public static PersianCalendar getInstance(Integer... fields) {
        return getInstance(Arrays.asList(fields));
    }

    //yyyy/mm/dd HH:MM:SS
    public String show(final String pattern) {
        String result = pattern;
        if (pattern.contains("yyyy"))
            result = result.replace("yyyy", String.valueOf(this.year));
        else if (pattern.contains("yy"))
            result = result.replace("yy", String.valueOf(this.year % 100));

        result = result.replace("mm", StringUtils.addLeadingZero(this.month, 2));
        result = result.replace("dd", StringUtils.addLeadingZero(this.day, 2));

        result = result.replace("HH", StringUtils.addLeadingZero(this.hour, 2));
        result = result.replace("MM", StringUtils.addLeadingZero(this.minute, 2));
        result = result.replace("SS", StringUtils.addLeadingZero(this.second, 2));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersianCalendar persianCalendar = (PersianCalendar) o;

        if (year != persianCalendar.year) return false;
        if (day != persianCalendar.day) return false;
        if (hour != persianCalendar.hour) return false;
        if (minute != persianCalendar.minute) return false;
        if (second != persianCalendar.second) return false;
        return month == persianCalendar.month;
    }

    @Override
    public int hashCode() {
        int result = year;
        result = 31 * result + month;
        result = 31 * result + day;
        result = 31 * result + hour;
        result = 31 * result + minute;
        result = 31 * result + second;
        return result;
    }

    @Override
    public String toString() {
        return show("yyyy/mm/dd-HH:MM:SS");
    }

}
