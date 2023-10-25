package com.khesam.certificate.exporter.prometheus;

import com.khesam.certificate.exporter.helper.CertificateHelper;
import com.khesam.certificate.exporter.helper.utils.date.DateUtils;
import io.prometheus.client.Gauge;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.text.DateFormatter;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.List;

@Singleton
public class PrometheusCertificateExporter {

    private final static String NOT_AVAILABLE = "N/A";
    private static final String DATE_FORMAT = "yyyy-mm-dd";

    private final Gauge certificateExpiry;
//    private final CertificateHelper certificateHelper;

    @Inject
    public PrometheusCertificateExporter(
            Gauge certificateExpiry
//            CertificateHelper certificateHelper
    ) {
        this.certificateExpiry = certificateExpiry;
//        this.certificateHelper = certificateHelper;
    }

    public void measureCertificateExpiry(String path, List<String> labels, double value) {
        certificateExpiry.labels(
                labels.toArray(new String[0])
        ).set(
                value
        );
    }

//    public void measureCertificateExpiry(String path, X509Certificate certificate) {
//        certificateExpiry.labels(
//                certificateHelper.getCommonName(certificate),
//                path,
//                DateUtils.gregorianToSolarHijri(certificate.getNotBefore()).show(DATE_FORMAT),
//                DateUtils.gregorianToSolarHijri(certificate.getNotAfter()).show(DATE_FORMAT)
//        ).set(
//                DateUtils.getDifferenceInDays(new Date(), certificate.getNotAfter())
//        );
//    }

    public void notAvailableData(String path) {
        this.certificateExpiry.labels(
                NOT_AVAILABLE,
                path,
                NOT_AVAILABLE,
                NOT_AVAILABLE
        ).set(
                -1
        );
    }
}
