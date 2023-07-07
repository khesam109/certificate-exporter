package com.khesam.certificate.exporter.scheduler;

import com.khesam.certificate.exporter.prometheus.PrometheusCertificateExporter;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.security.cert.X509Certificate;
import java.util.Map;

@Singleton
public class CertificateCollectorCallbackImpl implements CertificateCollectorCallback {

    private final PrometheusCertificateExporter prometheusCertificateExporter;

    @Inject
    public CertificateCollectorCallbackImpl(
            PrometheusCertificateExporter prometheusCertificateExporter
    ) {
        this.prometheusCertificateExporter = prometheusCertificateExporter;
    }

    @Override
    public void onSuccess(Map<String, X509Certificate> certificates) {
        certificates.forEach((key, value) -> {
            if (value == null) {
                prometheusCertificateExporter.notAvailableData(key);
            } else {
                prometheusCertificateExporter.measureCertificateExpiry(key, value);
            }
        });
    }
}
