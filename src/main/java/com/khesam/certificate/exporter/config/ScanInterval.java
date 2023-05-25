package com.khesam.certificate.exporter.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.concurrent.TimeUnit;

public record ScanInterval(
        @JsonProperty("period") int period,
        @JsonProperty("unit") String unit
) {

    @JsonIgnore
    public TimeUnit getTimeUnit() {
        if (this.unit.equalsIgnoreCase("SECOND"))
            return TimeUnit.SECONDS;
        else if (this.unit.equalsIgnoreCase("MINUTE")) {
            return TimeUnit.MINUTES;
        } else if (this.unit.equalsIgnoreCase("HOUR")) {
            return TimeUnit.HOURS;
        } else if (this.unit.equalsIgnoreCase("DAY")) {
            return TimeUnit.DAYS;
        } else {
            return null;
        }
    }
}
