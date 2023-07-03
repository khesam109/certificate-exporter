package com.khesam.certificate.exporter.config;

import java.util.Collections;
import java.util.List;

public class ApplicationParameter {

    private static final ScanInterval DEFAULT_INTERVAL = new ScanInterval(1, "DAY");
    private static ScanInterval scanInterval;
    private static List<CertificateDirectory> directories;
    private static List<RemoteEndpoint> endpoints;
    private static ServerConfig serverConfig;

    public static void registerDirectories(List<CertificateDirectory> directories) {
        if (directories != null && !directories.isEmpty()) {
            ApplicationParameter.directories = directories;
        } else {
            ApplicationParameter.directories = Collections.emptyList();
        }
    }

    public static void registerEndpoints(List<RemoteEndpoint> endpoints) {
        if (endpoints != null && !endpoints.isEmpty()) {
            ApplicationParameter.endpoints = endpoints;
        } else {
            ApplicationParameter.endpoints = Collections.emptyList();
        }
    }

    public static void setServerConfig(ServerConfig serverConfig) {
        ApplicationParameter.serverConfig = serverConfig;
    }

    public static void setScanInterval(ScanInterval scanInterval) {
        if (scanInterval == null) {
            ApplicationParameter.scanInterval = DEFAULT_INTERVAL;
        } else if (scanInterval.unit() == null) {
            ApplicationParameter.scanInterval = new ScanInterval(scanInterval.period(), "DAY");
        } else {
            ApplicationParameter.scanInterval = scanInterval;
        }
    }

    public static ScanInterval scanInterval() {
        return ApplicationParameter.scanInterval;
    }
    public static List<CertificateDirectory> directories() {
        return ApplicationParameter.directories;
    }

    public static List<RemoteEndpoint> endpoints() {
        return ApplicationParameter.endpoints;
    }

    public static ServerConfig serverConfig() {
        return ApplicationParameter.serverConfig;
    }
}
