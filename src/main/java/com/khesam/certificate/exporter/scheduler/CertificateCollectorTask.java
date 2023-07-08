package com.khesam.certificate.exporter.scheduler;

import com.khesam.certificate.exporter.collector.LocalDirectoryCertificateCollector;
import com.khesam.certificate.exporter.collector.RemoteEndpointCertificateCollector;
import com.khesam.certificate.exporter.config.TargetScan;
import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import org.tinylog.Logger;

import java.security.cert.X509Certificate;
import java.util.Date;
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
        Logger.info("Certificate collector scheduler has been started on %s", new Date().toString());

        Map<String, X509Certificate> certificates = new HashMap<>(
                this.localDirectoryCertificateCollector.collect(
                        this.targetScan.localTargets()
                )
        );

        certificates.putAll(this.remoteEndpointCertificateCollector.collect(this.targetScan.remoteTargets()));

        this.certificateCollectorCallback.onSuccess(certificates);

        Logger.info("Certificate collector scheduler just finished on %s", new Date().toString());
    }
}
