package com.khesam.certificate.exporter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;

public class ConfigReader {

    public static void init(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        File paramFile = new File(path);
        ExporterConfiguration config = mapper.readValue(paramFile, ExporterConfiguration.class);
        ApplicationParameter.registerDirectories(config.scanInterval(), config.directories());
    }
}
