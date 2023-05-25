package com.khesam.certificate.exporter.collector;

import com.khesam.certificate.exporter.config.ApplicationParameter;
import com.khesam.certificate.exporter.helper.CertificateHelper;
import com.khesam.certificate.exporter.helper.FileHelper;
import com.khesam.certificate.exporter.scheduler.CertificateCollectorCallback;

import java.io.File;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.Set;

public class CertificateCollector implements Runnable {

    private final CertificateCollectorCallback callback;

    public CertificateCollector(CertificateCollectorCallback callback) {
        this.callback = callback;
    }


    @Override
    public void run() {
        Set<File> files = new HashSet<>();
        ApplicationParameter.directories().forEach(
                dir -> files.addAll(
                        FileHelper.listInterestedFiles(dir.path(), dir.fileExtension())
                )
        );
        Set<X509Certificate> certificates = new HashSet<>();
        files.forEach(file -> {
            try {
                certificates.add(
                        CertificateHelper.readCertificateFile(file.getAbsolutePath())
                );
            } catch (IOException | CertificateException ignore) {
            }
        });
        this.callback.onSuccess(certificates);
    }
}
