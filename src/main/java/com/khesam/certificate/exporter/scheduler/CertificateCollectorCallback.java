package com.khesam.certificate.exporter.scheduler;

import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.List;

public interface CertificateCollectorCallback extends PeriodicTaskRunnerCallback {

    void onSuccess(Collection<X509Certificate> certificates);
    void onFiled(String message);
}
