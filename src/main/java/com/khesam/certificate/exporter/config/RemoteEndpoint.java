package com.khesam.certificate.exporter.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RemoteEndpoint(
        @JsonProperty("path") String path,
        @JsonProperty("ca_file_path") String caFile
) {
}
