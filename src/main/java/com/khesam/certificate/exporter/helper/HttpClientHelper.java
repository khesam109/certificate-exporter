package com.khesam.certificate.exporter.helper;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.SECONDS;

public class HttpClientHelper {
    private static final Duration CONNECTION_TIMEOUT = Duration.of(10, SECONDS);
    private static final Duration READ_TIMEOUT = Duration.of(10, SECONDS);

    private static HttpClientHelper INSTANCE;

    private HttpClientHelper() {}

    public static HttpClientHelper getInstance() {
        if (INSTANCE == null)
            INSTANCE = new HttpClientHelper();
        return INSTANCE;
    }

    public X509Certificate getCertificate(String path, String caFilePath) {
        try {
            Optional<SSLSession> optional = client(caFilePath).send(
                    getRequest(path),
                    HttpResponse.BodyHandlers.discarding()
            ).sslSession();

            if (optional.isPresent()) {
                return getEndEntityCertificate(optional.get());
            } else {
                return null;
            }

        } catch (IOException | InterruptedException | URISyntaxException e) {
            return null;
        }
    }

    private HttpClient client(String caFilePath) {
        return HttpClient.newBuilder()
                .sslContext(sslContext(caFilePath))
                .connectTimeout(CONNECTION_TIMEOUT)
                .build();
    }

    private SSLContext sslContext(String caFilePath) {
        try {
            InputStream is = new FileInputStream(caFilePath);
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate caCert = (X509Certificate)cf.generateCertificate(is);

            TrustManagerFactory tmf = TrustManagerFactory
                    .getInstance(TrustManagerFactory.getDefaultAlgorithm());
            KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
            ks.load(null);
            ks.setCertificateEntry("caCert", caCert);

            tmf.init(ks);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);
            return sslContext;
        } catch (KeyManagementException | IOException | KeyStoreException | NoSuchAlgorithmException |
                 CertificateException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpRequest getRequest(String path) throws URISyntaxException {
        return HttpRequest.newBuilder()
                .uri(new URI(path))
                .timeout(READ_TIMEOUT)
                .GET()
                .build();
    }

    private X509Certificate getEndEntityCertificate(SSLSession sslSession) throws SSLPeerUnverifiedException {
        if (sslSession.getPeerCertificates() == null || sslSession.getPeerCertificates().length == 0) {
            return null;
        }

        return (X509Certificate) sslSession.getPeerCertificates()[0];
    }
}
