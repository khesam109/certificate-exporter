package com.khesam.certificate.exporter.config;

import org.tinylog.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Singleton
public final class ConfigMapper {

    private static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.DAYS;
    private static final ExporterConfig.CalendarSystem DEFAULT_CALENDAR_SYSTEM = ExporterConfig.CalendarSystem.GREGORIAN;


    @Inject
    public ConfigMapper() {
    }

    ExporterConfig fromExporterConfigModel(ExporterConfigModel exporterConfigModel) {
        return new ExporterConfig(
                getCalendarSystem(exporterConfigModel.calendarSystem()),
                fromScanIntervalConfigModel(exporterConfigModel.scanIntervalConfigModel()),
                fromServerConfigModel(exporterConfigModel.serverConfigModel()),
                fromLocalDirectoryConfigModelAndRemoteEndPointConfigModel(
                        exporterConfigModel.localDirectoryConfigModels(),
                        exporterConfigModel.remoteEndPointConfigModels()
                )
        );
    }

    private ServerConfig fromServerConfigModel(
            ExporterConfigModel.ServerConfigModel serverConfigModel
    ) {
        return new ServerConfig(
                serverConfigModel.contextRoot(), serverConfigModel.port()
        );
    }

    private ScheduleConfig fromScanIntervalConfigModel(
            ExporterConfigModel.ScanIntervalConfigModel scanIntervalConfigModel
    ) {
        return new ScheduleConfig(
                scanIntervalConfigModel.period(),
                getTimeUnit(scanIntervalConfigModel.unit())
        );
    }

    private TargetScan fromLocalDirectoryConfigModelAndRemoteEndPointConfigModel(
            List<ExporterConfigModel.LocalDirectoryConfigModel> localDirectoryConfigModels,
            List<ExporterConfigModel.RemoteEndPointConfigModel> remoteEndPointConfigModels
    ) {
        return new TargetScan(
                localTargets(localDirectoryConfigModels),
                remoteTargets(remoteEndPointConfigModels)
        );
    }

    private ExporterConfig.CalendarSystem getCalendarSystem(String system) {
        return EnumSet.allOf(
                ExporterConfig.CalendarSystem.class
                ).stream().filter(
                        e  -> system.equalsIgnoreCase(e.name())
                ).findFirst().orElse(DEFAULT_CALENDAR_SYSTEM);
    }

    private TimeUnit getTimeUnit(String unit) {
        try {
            return TimeUnit.valueOf(
                    unit.toUpperCase(Locale.ROOT)
            );
        } catch (Exception ex) {
            Logger.error(ex, "Cannot convert time unit. So we decide to use default time unit (days)");
            return DEFAULT_TIME_UNIT;
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
