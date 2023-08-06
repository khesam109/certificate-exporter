package com.khesam.certificate.exporter.helper;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import org.tinylog.Logger;

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

public class ExporterHttpClient {

    private static final Duration CONNECTION_TIMEOUT = Duration.of(10, SECONDS);
    private static final Duration READ_TIMEOUT = Duration.of(10, SECONDS);

    private final HttpClient httpClient;

    @AssistedInject
    public ExporterHttpClient(@Assisted String caFilePath) {
        if (caFilePath == null || caFilePath.isEmpty()) {
            this.httpClient = buildHttpClient();
        } else {
            this.httpClient = buildHttpClient(sslContext(caFilePath));
        }
    }

    public X509Certificate getCertificate(String url) {
        try {
            Optional<SSLSession> optional = httpClient.send(
                    getRequest(url),
                    HttpResponse.BodyHandlers.discarding()
            ).sslSession();

            if (optional.isPresent()) {
                return getEndEntityCertificate(optional.get());
            } else {
                Logger.info("Cannot fetch certificate from {}", url);
                return null;
            }

        } catch (IOException | InterruptedException | URISyntaxException e) {
            Logger.error(e, "Failed to access {}.", url);
            return null;
        }
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

    private HttpClient buildHttpClient() {
        return HttpClient.newBuilder()
                .connectTimeout(CONNECTION_TIMEOUT)
                .build();
    }

    private HttpClient buildHttpClient(SSLContext sslContext) {
        return HttpClient.newBuilder()
                .sslContext(sslContext)
                .connectTimeout(CONNECTION_TIMEOUT)
                .build();
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
