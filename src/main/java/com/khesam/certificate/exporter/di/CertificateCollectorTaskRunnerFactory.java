package com.khesam.certificate.exporter.di;

import com.khesam.certificate.exporter.scheduler.CertificateCollectorTask;
import com.khesam.certificate.exporter.scheduler.CertificateCollectorTaskRunner;
import dagger.assisted.AssistedFactory;

import java.util.concurrent.TimeUnit;

@AssistedFactory
public interface CertificateCollectorTaskRunnerFactory {

    CertificateCollectorTaskRunner buildCertificateCollectorTaskRunner(
            int period,
            TimeUnit timeUnit,
            CertificateCollectorTask certificateCollectorTask
    );
}
