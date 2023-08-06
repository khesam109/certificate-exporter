package com.khesam.certificate.exporter.di;

import com.khesam.certificate.exporter.scheduler.callback.CertificateCollectorCallback;
import com.khesam.certificate.exporter.scheduler.callback.CertificateCollectorCallbackImpl;
import dagger.Binds;
import dagger.Module;

import javax.inject.Singleton;

@Module
public abstract class CertificateCollectorCallbackModule {

    @Binds
    @Singleton
    abstract CertificateCollectorCallback certificateCollectorCallbackImpl(
            CertificateCollectorCallbackImpl certificateCollectorCallbackImpl
    );
}
