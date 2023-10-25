package com.khesam.certificate.exporter.di.component;

import com.khesam.certificate.exporter.di.module.HijriDateFormatterModule;
import dagger.Component;

@Component(modules = HijriDateFormatterModule.class)
public interface HijriDateFormatterComponent extends DateFormatterComponent {
}
