package com.khesam.certificate.exporter.scheduler;

import com.khesam.certificate.exporter.collector.LocalDirectoryCertificateCollector;
import com.khesam.certificate.exporter.collector.RemoteEndpointCertificateCollector;
import com.khesam.certificate.exporter.config.TargetScan;
import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;

import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

public class CertificateCollectorTask implements Runnable {

    private final TargetScan targetScan;
    private final LocalDirectoryCertificateCollector localDirectoryCertificateCollector;
    private final RemoteEndpointCertificateCollector remoteEndpointCertificateCollector;
    private final CertificateCollectorCallback certificateCollectorCallback;

    @AssistedInject
    public CertificateCollectorTask(
            @Assisted TargetScan targetScan,
            LocalDirectoryCertificateCollector localDirectoryCertificateCollector,
            RemoteEndpointCertificateCollector remoteEndpointCertificateCollector,
            CertificateCollectorCallback certificateCollectorCallback
    ) {
        this.targetScan = targetScan;
        this.localDirectoryCertificateCollector = localDirectoryCertificateCollector;
        this.remoteEndpointCertificateCollector = remoteEndpointCertificateCollector;
        this.certificateCollectorCallback = certificateCollectorCallback;
    }

    @Override
    public void run() {
        Map<String, X509Certificate> certificates = new HashMap<>(this.localDirectoryCertificateCollector.collect(
                this.targetScan.localTargets()
        ));

        certificates.putAll(this.remoteEndpointCertificateCollector.collect(this.targetScan.remoteTargets()));

        this.certificateCollectorCallback.onSuccess(certificates);
    }
}
