package com.khesam.certificate.exporter.di;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class ObjectMapperModule {

    @Singleton
    @Provides
    ObjectMapper provideObjectMapper() {
        return new ObjectMapper(new YAMLFactory());
    }
}
