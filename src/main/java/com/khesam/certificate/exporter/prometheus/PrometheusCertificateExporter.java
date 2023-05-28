package com.khesam.certificate.exporter.prometheus;

import com.khesam.certificate.exporter.helper.CertificateHelper;
import com.khesam.certificate.exporter.helper.DateHelper;
import io.prometheus.client.Gauge;

import java.security.cert.X509Certificate;
import java.util.Date;

public class PrometheusCertificateExporter {

    private static final String DATE_FORMAT = "yyyy-mm-dd HH:MM";
    private static final String CERTIFICATE_EXPIRY_METRIC_NAME = "certificate_expiry_days";
    private static final String CERTIFICATE_EXPIRY_METRIC_HELP = "Measure Certificate Expire in Days";
    private static final String[] CERTIFICATE_EXPIRY_LABELS = new String[]
            {"common_name", "path", "effective_date", "expiration_date"};
    private final Gauge certificateExpiry;

    private static PrometheusCertificateExporter INSTANCE = null;

    private PrometheusCertificateExporter() {
        this.certificateExpiry = Gauge.build()
                .name(CERTIFICATE_EXPIRY_METRIC_NAME)
                .labelNames(CERTIFICATE_EXPIRY_LABELS)
                .help(CERTIFICATE_EXPIRY_METRIC_HELP)
                .register();
    }

    public static PrometheusCertificateExporter getInstance() {
        if (INSTANCE == null)
            INSTANCE = new PrometheusCertificateExporter();
        return INSTANCE;
    }

    public void measureCertificateExpiry(String filePath, X509Certificate certificate) {
        this.certificateExpiry.labels(
                CertificateHelper.getCommonName(certificate),
                filePath,
                DateHelper.gregorianToSolarHijri(certificate.getNotBefore()).show(DATE_FORMAT),
                DateHelper.gregorianToSolarHijri(certificate.getNotAfter()).show(DATE_FORMAT)
        ).set(
                DateHelper.getDifferenceInDays(new Date(), certificate.getNotAfter())
        );
    }
}
