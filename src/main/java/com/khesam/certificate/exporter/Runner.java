package com.khesam.certificate.exporter;

import com.khesam.certificate.exporter.config.ConfigReader;
import com.khesam.certificate.exporter.config.ScheduleConfig;
import com.khesam.certificate.exporter.config.ServerConfig;
import com.khesam.certificate.exporter.config.TargetScan;
import com.khesam.certificate.exporter.di.component.ApplicationDependencyComponent;
import com.khesam.certificate.exporter.di.component.DaggerApplicationDependencyComponent;
import com.khesam.certificate.exporter.scheduler.CertificateCollectorScheduler;
import com.sun.net.httpserver.HttpServer;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.HTTPServer;
import org.tinylog.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Runner {

    public static void main(String[] args) {
        ApplicationDependencyComponent factory = DaggerApplicationDependencyComponent.create();
        ConfigReader configReader = factory.configReader();
        CertificateCollectorScheduler certificateCollectorScheduler = factory.certificateCollectorScheduler();

        try {
            configReader.init();
            ServerConfig serverConfig = configReader.serverConfig();
            ScheduleConfig scheduleConfig = configReader.scheduleConfig();
            TargetScan targetScan = configReader.targetScan();

            runServer(serverConfig);

            startScheduling(certificateCollectorScheduler, scheduleConfig, targetScan);

        } catch (IOException ex) {
            Logger.error(ex, "Failed to read config");
            System.exit(1);
        } catch (IllegalStateException ex) {
            Logger.error("Please init config reader first");
            System.exit(1);
        }
    }

    private static void runServer(ServerConfig serverConfig) {
        Logger.info("Starting exporter...");

        HTTPServer server = null;
        try {
            HttpServer httpServer = HttpServer.create(
                    new InetSocketAddress(
                            serverConfig.port()
                    ), 3
            );

            httpServer.createContext(
                    serverConfig.contextRoot(),
                    new HTTPServer.HTTPMetricHandler(CollectorRegistry.defaultRegistry)
            );

            server = new HTTPServer.Builder()
                    .withHttpServer(httpServer)
                    .build();

            Logger.info("Http endpoint successfully started on port {}", serverConfig.port());
        } catch (IOException e) {
            Logger.error(e, "Failed to start http server");
        } finally {
            if (server != null) {
                server.close();
            }
        }
    }

    private static void startScheduling(
            CertificateCollectorScheduler certificateCollectorScheduler,
            ScheduleConfig scheduleConfig,
            TargetScan targetScan
    ) {
        certificateCollectorScheduler.getCertificateCollectorTaskRunner(scheduleConfig, targetScan).run();
    }
}
