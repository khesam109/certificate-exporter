package com.khesam.certificate.exporter.helper;

import com.khesam.certificate.exporter.di.HttpClientFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.security.cert.X509Certificate;

@Singleton
public class HttpHelper {

    private final HttpClientFactory httpClientFactory;

    @Inject
    public HttpHelper(HttpClientFactory httpClientFactory) {
        this.httpClientFactory = httpClientFactory;
    }

    public X509Certificate getCertificate(String url, String caFilePath) {
        return httpClientFactory.buildExporterHttpClient(caFilePath).getCertificate(url);
    }
}
