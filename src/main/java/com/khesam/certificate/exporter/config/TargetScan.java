package com.khesam.certificate.exporter.config;

import java.util.List;

public record TargetScan(
        List<LocalTarget> localTargets,
        List<RemoteTarget> remoteTargets
) {

    public record LocalTarget(
            String path,
            List<String> fileExtensions
    ) {
    }

    public record RemoteTarget(
            String url,
            String caFilePath
    ) {

    }
}
