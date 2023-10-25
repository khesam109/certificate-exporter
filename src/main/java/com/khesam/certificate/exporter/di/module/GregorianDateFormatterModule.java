package com.khesam.certificate.exporter.di.module;

import com.khesam.certificate.exporter.helper.formatter.DateFormatter;
import com.khesam.certificate.exporter.helper.formatter.impl.GregorianDateFormatter;
import dagger.Binds;
import dagger.Module;

import javax.inject.Singleton;

@Module
public interface GregorianDateFormatterModule {

    @Binds
    @Singleton
    DateFormatter dateFormatter(GregorianDateFormatter dateFormatter);
}
