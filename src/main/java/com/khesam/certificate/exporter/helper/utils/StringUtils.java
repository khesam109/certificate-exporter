package com.khesam.certificate.exporter.helper.utils;

public class StringUtils {

    public static String addLeadingZero(int input, int length) {
        StringBuilder result = new StringBuilder(String.valueOf(input));
        while (result.length() < length)
            result.insert(0, "0");
        return result.toString();
    }
}
