package com.khesam.certificate.exporter.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

record ExporterConfigModel(
        @JsonProperty("server_config") ServerConfigModel serverConfigModel,
        @JsonProperty("scan_interval") ScanIntervalConfigModel scanIntervalConfigModel,
        @JsonProperty("directories") List<LocalDirectoryConfigModel> localDirectoryConfigModels,
        @JsonProperty("remote_endpoints") List<RemoteEndPointConfigModel> remoteEndPointConfigModels
) {

    record ServerConfigModel(
            @JsonProperty("context_root") String contextRoot,
            @JsonProperty("port") int port
    ) {
    }

    record ScanIntervalConfigModel(
            @JsonProperty("period") int period,
            @JsonProperty("unit") String unit
    ) {
    }

    record LocalDirectoryConfigModel(
            @JsonProperty("path") String path,
            @JsonProperty("file_extensions") List<String> fileExtensions
    ) {
    }

    record RemoteEndPointConfigModel(
            @JsonProperty("path") String path,
            @JsonProperty("ca_file_path") String caFilePath
    ) {
    }
}
