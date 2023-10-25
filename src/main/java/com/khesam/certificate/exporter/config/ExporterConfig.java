package com.khesam.certificate.exporter.config;

public record ExporterConfig(

        CalendarSystem calendarSystem,
        ScheduleConfig scheduleConfig,
        ServerConfig serverConfig,
        TargetScan targetScan
) {

    public enum CalendarSystem {
        HIJRI,
        GREGORIAN
    }
}
