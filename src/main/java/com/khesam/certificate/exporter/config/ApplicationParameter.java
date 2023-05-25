package com.khesam.certificate.exporter.config;

import java.util.Collections;
import java.util.List;

public class ApplicationParameter {

    private static final ScanInterval DEFAULT_INTERVAL = new ScanInterval(1, "DAY");
    private static ScanInterval scanInterval;
    private static List<CertificateDirectory> directories;

    public static void registerDirectories(ScanInterval scanInterval, List<CertificateDirectory> directories) {
        if (scanInterval == null) {
            ApplicationParameter.scanInterval = DEFAULT_INTERVAL;
        } else if (scanInterval.unit() == null) {
            ApplicationParameter.scanInterval = new ScanInterval(scanInterval.period(), "DAY");
        } else {
            ApplicationParameter.scanInterval = scanInterval;
        }

        if (directories != null && !directories.isEmpty()) {
            ApplicationParameter.directories = directories;
        } else {
            ApplicationParameter.directories = Collections.emptyList();
        }
    }

    public static ScanInterval scanInterval() {
        return ApplicationParameter.scanInterval;
    }
    public static List<CertificateDirectory> directories() {
        return ApplicationParameter.directories;
    }
}
