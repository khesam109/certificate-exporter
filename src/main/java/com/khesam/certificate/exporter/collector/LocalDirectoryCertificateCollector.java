package com.khesam.certificate.exporter.collector;

import com.khesam.certificate.exporter.config.TargetScan;
import com.khesam.certificate.exporter.helper.CertificateHelper;
import com.khesam.certificate.exporter.helper.FileHelper;
import org.tinylog.Logger;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

public class LocalDirectoryCertificateCollector implements CertificateCollector<TargetScan.LocalTarget> {

    private final FileHelper fileHelper;
    private final CertificateHelper certificateHelper;

    @Inject
    public LocalDirectoryCertificateCollector(
            FileHelper fileHelper,
            CertificateHelper certificateHelper
    ) {
        this.fileHelper = fileHelper;
        this.certificateHelper = certificateHelper;
    }

    @Override
    public Map<String, X509Certificate> collect(List<TargetScan.LocalTarget> targets) {
        Set<File> files = getAllCertificates(targets);

        Map<String, X509Certificate> certificates = new HashMap<>();
        files.forEach(file -> {
            try {
                certificates.put(file.getAbsolutePath(),
                        certificateHelper.readCertificateFile(file.getAbsolutePath())
                );
            } catch (IOException | CertificateException e) {
                Logger.error(e, String.format("Failed to read file as X509 certificate. \n%s", file.getAbsolutePath()));
            }
        });

        return certificates;
    }

    private Set<File> getAllCertificates(List<TargetScan.LocalTarget> targets) {
        Set<File> files = new HashSet<>();
        targets.forEach(dir -> files.addAll(
                fileHelper.getInterestedFiles(dir.path(), dir.fileExtensions())
        ));

        return files;
    }
}
