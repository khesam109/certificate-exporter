package com.khesam.certificate.exporter.collector;

import com.khesam.certificate.exporter.config.TargetScan;
import com.khesam.certificate.exporter.helper.HttpHelper;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class RemoteEndpointCertificateCollector implements CertificateCollector<TargetScan.RemoteTarget> {

    private final HttpHelper httpHelper;

    @Inject
    public RemoteEndpointCertificateCollector(HttpHelper httpHelper) {
        this.httpHelper = httpHelper;
    }

    @Override
    public Map<String, X509Certificate> collect(List<TargetScan.RemoteTarget> targets) {
        Map<String, X509Certificate> certificates = new HashMap<>();
        targets.forEach(endpoint -> {
            certificates.put(endpoint.url(), httpHelper.getCertificate(endpoint.url(), endpoint.caFilePath()));
        });

        return certificates;
    }
}
