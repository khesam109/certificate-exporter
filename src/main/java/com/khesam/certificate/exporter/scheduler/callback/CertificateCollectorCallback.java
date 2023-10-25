package com.khesam.certificate.exporter.scheduler.callback;

import com.khesam.certificate.exporter.helper.formatter.DateFormatter;
import com.khesam.certificate.exporter.helper.utils.date.DateUtils;
import com.khesam.certificate.exporter.prometheus.PrometheusCertificateExporter;
import org.tinylog.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Singleton
public class CertificateCollectorCallback implements PeriodicTaskRunnerCallback {

//    private final PrometheusCertificateExporter prometheusCertificateExporter;
    private final DateFormatter dateFormatter;

    @Inject
    public CertificateCollectorCallback(
//            PrometheusCertificateExporter prometheusCertificateExporter,
            DateFormatter dateFormatter
    ) {
//        this.prometheusCertificateExporter = prometheusCertificateExporter;
        this.dateFormatter = dateFormatter;
    }

    public void onSuccess(Map<String, X509Certificate> certificates) {
        Logger.info(String.format("%d certificate(s) has been found.", certificates.size()));
        certificates.forEach((key, value) -> {
//            if (value == null) {
//                prometheusCertificateExporter.notAvailableData(key);
//            } else {
//                prometheusCertificateExporter.measureCertificateExpiry(
//                        key,
//                        List.of(DateUtils.gregorianToSolarHijri(value.getNotBefore()).show("yyyy-mm-dd"),
//                                DateUtils.gregorianToSolarHijri(value.getNotAfter()).show("yyyy-mm-dd")),
//                        DateUtils.getDifferenceInDays(new Date(), value.getNotAfter())
//                );
//            }
        });
    }
    public void onFiled(String message) {
        Logger.error(message);
    }
}
