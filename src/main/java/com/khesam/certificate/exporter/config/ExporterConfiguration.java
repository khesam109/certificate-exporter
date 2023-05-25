package com.khesam.certificate.exporter.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ExporterConfiguration(
        @JsonProperty("scan-interval") ScanInterval scanInterval,
        @JsonProperty("directories") List<CertificateDirectory> directories
) {
}
