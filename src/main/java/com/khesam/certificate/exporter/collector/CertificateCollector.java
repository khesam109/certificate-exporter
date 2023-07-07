package com.khesam.certificate.exporter.collector;

import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

public interface CertificateCollector<T> {

    Map<String, X509Certificate> collect(List<T> target);
}
