package com.khesam.certificate.exporter.di.module;

import dagger.Module;
import dagger.Provides;
import io.prometheus.client.Gauge;

import javax.inject.Singleton;

@Module
public class PrometheusMetricModule {

    private static final String CERTIFICATE_EXPIRY_METRIC_NAME = "certificate_expiry_days";
    private static final String CERTIFICATE_EXPIRY_METRIC_HELP = "Measure Certificate Expire in Days";
    private static final String[] CERTIFICATE_EXPIRY_LABELS = new String[] {
            "common_name", "path", "effective_date", "expiration_date"
    };

    @Provides
    @Singleton
    public Gauge provideGauge() {
        return Gauge.build()
                .name(CERTIFICATE_EXPIRY_METRIC_NAME)
                .labelNames(CERTIFICATE_EXPIRY_LABELS)
                .help(CERTIFICATE_EXPIRY_METRIC_HELP)
                .register();
    }
}
