package com.khesam.certificate.exporter.scheduler;

import com.khesam.certificate.exporter.config.ScheduleConfig;
import com.khesam.certificate.exporter.di.CertificateCollectorTaskRunnerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CertificateCollectorScheduler {

    private final CertificateCollectorTaskRunnerFactory certificateCollectorTaskRunnerFactory;

    @Inject
    public CertificateCollectorScheduler(
            CertificateCollectorTaskRunnerFactory certificateCollectorTaskRunnerFactory
    ) {
        this.certificateCollectorTaskRunnerFactory = certificateCollectorTaskRunnerFactory;
    }

    public CertificateCollectorTaskRunner getCertificateCollectorTaskRunner(ScheduleConfig scheduleConfig) {
        return certificateCollectorTaskRunnerFactory.buildCertificateCollectorTaskRunner(
                scheduleConfig.period(), scheduleConfig.unit()
        );
    }
}
