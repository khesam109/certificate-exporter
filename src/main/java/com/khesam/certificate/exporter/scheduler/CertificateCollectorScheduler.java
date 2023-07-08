package com.khesam.certificate.exporter.scheduler;

import com.khesam.certificate.exporter.config.ScheduleConfig;
import com.khesam.certificate.exporter.config.TargetScan;
import com.khesam.certificate.exporter.di.CertificateCollectorTaskFactory;
import com.khesam.certificate.exporter.di.CertificateCollectorTaskRunnerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CertificateCollectorScheduler {

    private final CertificateCollectorTaskRunnerFactory certificateCollectorTaskRunnerFactory;
    private final CertificateCollectorTaskFactory certificateCollectorTaskFactory;

    @Inject
    public CertificateCollectorScheduler(
            CertificateCollectorTaskRunnerFactory certificateCollectorTaskRunnerFactory,
            CertificateCollectorTaskFactory certificateCollectorTaskFactory
    ) {
        this.certificateCollectorTaskRunnerFactory = certificateCollectorTaskRunnerFactory;
        this.certificateCollectorTaskFactory = certificateCollectorTaskFactory;
    }

    public CertificateCollectorTaskRunner getCertificateCollectorTaskRunner(
            ScheduleConfig scheduleConfig, TargetScan targetScan
    ) {
        return certificateCollectorTaskRunnerFactory.buildCertificateCollectorTaskRunner(
                scheduleConfig.period(), scheduleConfig.unit(),
                certificateCollectorTaskFactory.buildCertificateCollectorTask(targetScan)
        );
    }
}
