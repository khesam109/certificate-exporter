package com.khesam.certificate.exporter.di.component;

import com.khesam.certificate.exporter.di.module.GregorianDateFormatterModule;
import com.khesam.certificate.exporter.di.module.HijriDateFormatterModule;
import com.khesam.certificate.exporter.di.module.PrometheusMetricModule;
import com.khesam.certificate.exporter.scheduler.callback.CertificateCollectorCallback;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(dependencies = DateFormatterComponent.class/*, modules = {
        GregorianDateFormatterModule.class,
        HijriDateFormatterModule.class,
        PrometheusMetricModule.class
}*/)
public interface CertificateCollectorCallbackComponent {

    CertificateCollectorCallback certificateCollectorCallback();
}
