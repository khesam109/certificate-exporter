package com.khesam.certificate.exporter.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;

@Singleton
public class ConfigReader {

    private static final String CONFIG_PATH_KEY = "EXPORTER_CONFIG_PATH";
    private static final String DEFAULT_CONFIG_PATH = "/etc/certificate-exporter/config.yml";

    private final ObjectMapper objectMapper;
    private final ConfigMapper configMapper;

    private ServerConfig serverConfig;
    private ScheduleConfig scheduleConfig;
    private TargetScan targetScan;

    @Inject
    public ConfigReader(
            ObjectMapper objectMapper,
            ConfigMapper configMapper
    ) {
        this.objectMapper = objectMapper;
        this.configMapper = configMapper;
    }

    public void init() throws IOException {
        ExporterConfigModel exporterConfigModel = objectMapper.readValue(configFile(), ExporterConfigModel.class);

        this.serverConfig = configMapper.fromServerConfigModel(
                exporterConfigModel.serverConfigModel()
        );

        this.scheduleConfig = configMapper.fromScanIntervalConfigModel(
                exporterConfigModel.scanIntervalConfigModel()
        );

        this.targetScan = configMapper.fromLocalDirectoryConfigModelAndRemoteEndPointConfigModel(
                exporterConfigModel.localDirectoryConfigModels(),
                exporterConfigModel.remoteEndPointConfigModels()
        );
    }

    public ServerConfig serverConfig() {
        if (this.serverConfig == null)
            throw new IllegalStateException("Server config was not load due to unknown error");
        return this.serverConfig;
    }

    public ScheduleConfig scheduleConfig() {
        if (this.scheduleConfig == null)
            throw new IllegalStateException("Schedule config was not load due to unknown error");
        return this.scheduleConfig;
    }

    public TargetScan targetScan() {
        if (this.targetScan == null)
            throw new IllegalStateException("Targets was not load due to unknown error");
        return this.targetScan;
    }

    private File configFile() {
        String configPath = System.getenv(CONFIG_PATH_KEY);
        if (configPath != null && !configPath.isEmpty())
            return new File(configPath);
        return new File(DEFAULT_CONFIG_PATH);
    }
}
