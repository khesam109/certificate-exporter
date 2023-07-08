package com.khesam.certificate.exporter.di;

import com.khesam.certificate.exporter.config.TargetScan;
import com.khesam.certificate.exporter.scheduler.CertificateCollectorTask;
import dagger.assisted.AssistedFactory;

@AssistedFactory
public interface CertificateCollectorTaskFactory {

    CertificateCollectorTask buildCertificateCollectorTask(TargetScan targetScan);
}
