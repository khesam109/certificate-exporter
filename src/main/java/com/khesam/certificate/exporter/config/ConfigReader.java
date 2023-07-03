package com.khesam.certificate.exporter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;

public class ConfigReader {

    private static final String CONFIG_PATH_KEY = "EXPORTER_CONFIG_PATH";
//    private static final String DEFAULT_CONFIG_PATH = "/etc/certificateexporter/config.yml";
    private static final String DEFAULT_CONFIG_PATH = "F:\\Personal\\github\\certificate-exporter\\src\\main\\resources\\config.yml";

    public static void init() throws IOException {
        String configPath = System.getenv(CONFIG_PATH_KEY);
        if (configPath == null || configPath.isEmpty()) {
            init(DEFAULT_CONFIG_PATH);
        } else {
            init(configPath);
        }
    }

    private static void init(String exporterConfig) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        ExporterConfiguration config = getExporterConfiguration(mapper, new File(exporterConfig));
        ApplicationParameter.registerDirectories(config.directories());
        ApplicationParameter.registerEndpoints(config.endpoints());
        ApplicationParameter.setScanInterval(config.scanInterval());
        ApplicationParameter.setServerConfig(config.serverConfig());
    }

    private static ExporterConfiguration getExporterConfiguration(ObjectMapper mapper, File exporterConfigFile) throws IOException {
        return mapper.readValue(exporterConfigFile, ExporterConfiguration.class);
    }
}
