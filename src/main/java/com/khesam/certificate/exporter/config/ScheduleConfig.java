package com.khesam.certificate.exporter.config;

import java.util.concurrent.TimeUnit;

public record ScheduleConfig(
        int period,
        TimeUnit unit
) {
}
