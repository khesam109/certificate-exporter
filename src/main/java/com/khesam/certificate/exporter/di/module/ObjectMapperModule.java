package com.khesam.certificate.exporter.di.module;

import com.fasterxml.jackson.databind.DeserializationFeature;
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
        ObjectMapper mapper = new  ObjectMapper(
                new YAMLFactory()
        );
        mapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
        return mapper;
    }
}
