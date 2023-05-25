package com.khesam.certificate.exporter.scheduler;

import com.khesam.certificate.exporter.config.ApplicationParameter;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PeriodicTaskRunner {

    private static PeriodicTaskRunner INSTANCE;

    private final int period;
    private final TimeUnit timeUnit;
    private final ScheduledExecutorService executor;

    private PeriodicTaskRunner() {
        this.period = ApplicationParameter.scanInterval().period();
        this.timeUnit = ApplicationParameter.scanInterval().getTimeUnit();
        this.executor = Executors.newScheduledThreadPool(1);
    }

    public static PeriodicTaskRunner getInstance() {
        if (INSTANCE == null)
            INSTANCE = new PeriodicTaskRunner();
        return INSTANCE;
    }

    public void run(Runnable task) {
        this.executor.scheduleAtFixedRate(
                task, 0, period, timeUnit
        );
    }
}
