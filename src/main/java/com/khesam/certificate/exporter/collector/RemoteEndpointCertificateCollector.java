package com.khesam.certificate.exporter.collector;

import com.khesam.certificate.exporter.config.TargetScan;
import com.khesam.certificate.exporter.helper.HttpHelper;
import org.tinylog.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.security.cert.X509Certificate;
import java.util.Date;
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
        Logger.info("Remote Certificate collector job has been started at {}", new Date().toString());

        Map<String, X509Certificate> certificates = new HashMap<>();
        targets.forEach(endpoint -> certificates.put(
                endpoint.url(),
                httpHelper.getCertificate(
                        endpoint.url(), endpoint.caFilePath()
                ))
        );

        Logger.info(String.format("Remote Certificate collector found %d certificate(s)", certificates.size()));

        return certificates;
    }
}
