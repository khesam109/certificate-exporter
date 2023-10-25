package com.khesam.certificate.exporter.di.component;

import com.khesam.certificate.exporter.config.ConfigReader;
import com.khesam.certificate.exporter.di.module.CertificateCollectorModule;
import com.khesam.certificate.exporter.di.module.ObjectMapperModule;
import com.khesam.certificate.exporter.di.module.PrometheusMetricModule;
import com.khesam.certificate.exporter.scheduler.CertificateCollectorScheduler;
import dagger.Component;

import javax.inject.Singleton;


@Singleton
@Component(modules = {
//        CertificateCollectorCallbackModule.class,
        CertificateCollectorModule.class,
        ObjectMapperModule.class,
        CertificateCollectorModule.class,
        PrometheusMetricModule.class
})
public interface ApplicationDependencyComponent {

    ConfigReader configReader();
    CertificateCollectorScheduler certificateCollectorScheduler();
}
