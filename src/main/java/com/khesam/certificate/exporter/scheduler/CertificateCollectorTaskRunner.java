package com.khesam.certificate.exporter.scheduler;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;

import java.util.concurrent.TimeUnit;

public class CertificateCollectorTaskRunner extends PeriodicTaskRunner {

    @AssistedInject
    public CertificateCollectorTaskRunner(
            @Assisted int period,
            @Assisted TimeUnit timeUnit,
            @Assisted CertificateCollectorTask certificateCollectorTask
    ) {
        super(period, timeUnit, certificateCollectorTask);
    }
}
