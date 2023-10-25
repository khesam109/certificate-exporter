package com.khesam.certificate.exporter.di.component;

import com.khesam.certificate.exporter.di.module.GregorianDateFormatterModule;
import dagger.Component;

@Component(modules = GregorianDateFormatterModule.class)
public interface GregorianDateFormatterComponent extends DateFormatterComponent {
}
