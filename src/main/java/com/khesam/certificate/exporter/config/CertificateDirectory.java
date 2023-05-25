package com.khesam.certificate.exporter.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CertificateDirectory(
        @JsonProperty("path") String path,
        @JsonProperty("file-extension") List<String> fileExtension
) {
}
