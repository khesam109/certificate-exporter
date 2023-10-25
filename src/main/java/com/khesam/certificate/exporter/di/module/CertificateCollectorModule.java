package com.khesam.certificate.exporter.di.module;

import com.khesam.certificate.exporter.collector.CertificateCollector;
import com.khesam.certificate.exporter.collector.LocalDirectoryCertificateCollector;
import com.khesam.certificate.exporter.collector.RemoteEndpointCertificateCollector;
import com.khesam.certificate.exporter.config.TargetScan;
import dagger.Binds;
import dagger.Module;

import javax.inject.Singleton;

@Module
public abstract class CertificateCollectorModule {

    @Binds
    @Singleton
    abstract CertificateCollector<TargetScan.LocalTarget> localDirectoryCertificateCollector(
            LocalDirectoryCertificateCollector certificateCollector
    );

    @Binds
    @Singleton
    abstract CertificateCollector<TargetScan.RemoteTarget> remoteEndpointCertificateCollector(
            RemoteEndpointCertificateCollector certificateCollector
    );
}
