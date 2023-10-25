package com.khesam.certificate.exporter.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.List;

@JsonRootName("exporter")
record ExporterConfigModel(
        @JsonProperty("calendar_system") String calendarSystem,
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
