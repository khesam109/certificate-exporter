package com.khesam.certificate.exporter.collector;

import com.khesam.certificate.exporter.config.ApplicationParameter;
import com.khesam.certificate.exporter.helper.CertificateHelper;
import com.khesam.certificate.exporter.helper.FileHelper;
import com.khesam.certificate.exporter.helper.HttpClientHelper;
import com.khesam.certificate.exporter.scheduler.CertificateCollectorCallback;
import org.tinylog.Logger;

import java.io.File;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CertificateCollector implements Runnable {

    private final CertificateCollectorCallback callback;

    public CertificateCollector(CertificateCollectorCallback callback) {
        this.callback = callback;
    }


    @Override
    public void run() {
        Logger.info("Collecting certificates job stated...");

        Map<String, X509Certificate> certificates = collectLocalCertificates();
        certificates.putAll(collectRemoteCertificates());

        Logger.info("Collecting certificates job finished. {} certificated has been found.", certificates.size());
        this.callback.onSuccess(certificates);
    }

    private Map<String, X509Certificate> collectLocalCertificates() {
        Set<File> files = new HashSet<>();
        ApplicationParameter.directories().forEach(
                dir -> files.addAll(
                        FileHelper.listInterestedFiles(dir.path(), dir.fileExtension())
                )
        );
        Map<String, X509Certificate> certificates = new HashMap<>();
        files.forEach(file -> {
            try {
                certificates.put(file.getAbsolutePath(),
                        CertificateHelper.readCertificateFile(file.getAbsolutePath())
                );
            } catch (IOException | CertificateException ignore) {
            }
        });

        return certificates;
    }

    private Map<String, X509Certificate> collectRemoteCertificates() {
        Map<String, X509Certificate> certificates = new HashMap<>();
        ApplicationParameter.endpoints().forEach(
                endpoint -> certificates.put(
                        endpoint.path(),
                        HttpClientHelper.getInstance().getCertificate(endpoint.path(), endpoint.caFile())
                )
        );

        return certificates;
    }
}
