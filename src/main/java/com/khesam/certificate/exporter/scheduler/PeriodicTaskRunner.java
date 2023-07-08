package com.khesam.certificate.exporter.scheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class PeriodicTaskRunner {

    private final int period;
    private final TimeUnit timeUnit;
    private final Runnable task;
    private final ScheduledExecutorService executor;

    public  PeriodicTaskRunner(int period, TimeUnit timeUnit, Runnable task) {
        this.period = period;
        this.timeUnit = timeUnit;
        this.task = task;
        this.executor = Executors.newScheduledThreadPool(1);
    }

    public void run() {
        this.executor.scheduleAtFixedRate(
                this.task, 0, period, timeUnit
        );
    }
}
