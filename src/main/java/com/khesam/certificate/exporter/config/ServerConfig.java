package com.khesam.certificate.exporter.config;

public record ServerConfig(
        String contextRoot,
        int port
) {
}
