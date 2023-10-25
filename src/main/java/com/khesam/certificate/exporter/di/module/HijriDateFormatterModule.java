package com.khesam.certificate.exporter.di.module;

import com.khesam.certificate.exporter.helper.formatter.DateFormatter;
import com.khesam.certificate.exporter.helper.formatter.impl.HijriDateFormatter;
import dagger.Binds;
import dagger.Module;

import javax.inject.Singleton;

@Module
public interface HijriDateFormatterModule {

    @Binds
    @Singleton
    DateFormatter dateFormatter(HijriDateFormatter dateFormatter);
}
