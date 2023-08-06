package com.khesam.certificate.exporter.config;

import org.tinylog.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Singleton
final class ConfigMapper {

    @Inject
    public ConfigMapper() {
    }

    ServerConfig fromServerConfigModel(
            ExporterConfigModel.ServerConfigModel serverConfigModel
    ) {
        return new ServerConfig(
                serverConfigModel.contextRoot(), serverConfigModel.port()
        );
    }

    ScheduleConfig fromScanIntervalConfigModel(
            ExporterConfigModel.ScanIntervalConfigModel scanIntervalConfigModel
    ) {
        return new ScheduleConfig(
                scanIntervalConfigModel.period(),
                getTimeUnit(scanIntervalConfigModel.unit())
        );
    }

    TargetScan fromLocalDirectoryConfigModelAndRemoteEndPointConfigModel(
            List<ExporterConfigModel.LocalDirectoryConfigModel> localDirectoryConfigModels,
            List<ExporterConfigModel.RemoteEndPointConfigModel> remoteEndPointConfigModels
    ) {
        return new TargetScan(
                localTargets(localDirectoryConfigModels),
                remoteTargets(remoteEndPointConfigModels)
        );
    }

    private TimeUnit getTimeUnit(String unit) {
        try {
            return TimeUnit.valueOf(
                    unit.toUpperCase(Locale.ROOT)
            );
        } catch (Exception ex) {
            Logger.error(ex, "Cannot convert time unit. So we decide to use default time unit");
            return null;
        }
    }

    private List<TargetScan.LocalTarget> localTargets(
            List<ExporterConfigModel.LocalDirectoryConfigModel> localDirectoryConfigModels
    ) {
        if (localDirectoryConfigModels == null || localDirectoryConfigModels.isEmpty()) {
            return Collections.emptyList();
        }

        return localDirectoryConfigModels.stream().map(
                e -> new TargetScan.LocalTarget(e.path(), e.fileExtensions())
        ).collect(Collectors.toList());
    }

    private List<TargetScan.RemoteTarget> remoteTargets(
            List<ExporterConfigModel.RemoteEndPointConfigModel> remoteEndPointConfigModels
    ) {
        if (remoteEndPointConfigModels == null || remoteEndPointConfigModels.isEmpty()) {
            return Collections.emptyList();
        }
        return remoteEndPointConfigModels.stream().map(
                e -> new TargetScan.RemoteTarget(e.path(), e.caFilePath())
        ).collect(Collectors.toList());
    }
}
