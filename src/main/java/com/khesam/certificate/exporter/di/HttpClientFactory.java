package com.khesam.certificate.exporter.di;

import com.khesam.certificate.exporter.helper.ExporterHttpClient;
import dagger.assisted.AssistedFactory;

@AssistedFactory
public interface HttpClientFactory {

    ExporterHttpClient buildExporterHttpClient(String caFilePath);
}
