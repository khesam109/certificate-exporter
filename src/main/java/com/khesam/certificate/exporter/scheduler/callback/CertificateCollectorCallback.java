package com.khesam.certificate.exporter.scheduler.callback;

import org.tinylog.Logger;

import java.security.cert.X509Certificate;
import java.util.Map;

public interface CertificateCollectorCallback extends PeriodicTaskRunnerCallback {

    void onSuccess(Map<String, X509Certificate> certificates);
    default void onFiled(String message) {
        Logger.error(message);
    }
}
