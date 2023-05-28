package com.khesam.certificate.exporter;

import com.khesam.certificate.exporter.collector.CertificateCollector;
import com.khesam.certificate.exporter.config.ApplicationParameter;
import com.khesam.certificate.exporter.config.ConfigReader;
import com.khesam.certificate.exporter.prometheus.PrometheusCertificateExporter;
import com.khesam.certificate.exporter.scheduler.PeriodicTaskRunner;
import com.sun.net.httpserver.HttpServer;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.HTTPServer;
import org.tinylog.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Runner {

//    static final Gauge gauge = Gauge.build()
//            .name("cert").labelNames("Path", "Asghar").help("Inprogress requests.").register();

//    static void processRequest(String filePath, X509Certificate x509Certificate) {
//        gauge.labels(filePath, CertificateHelper.getCommonName(x509Certificate)).set(DateHelper.getDifferenceInDays(new Date(), x509Certificate.getNotAfter()));
//    }

    public static void main(String[] args) {
        try {
            ConfigReader.init();
            runServer();
            runScheduler();
        } catch (Exception e) {
            Logger.error(e, "Unable to start exporter due to miss configuration");
            System.exit(1);
        }
    }

    private static void runServer() throws IOException {
        Logger.info("Starting exporter...");
        HttpServer httpServer = HttpServer.create(
                new InetSocketAddress(
                        ApplicationParameter.serverConfig().port()
                ), 3
        );

        httpServer.createContext(
                ApplicationParameter.serverConfig().rootContext(),
                new HTTPServer.HTTPMetricHandler(CollectorRegistry.defaultRegistry)
        );

        new HTTPServer.Builder()
                .withHttpServer(httpServer)
                .build();

        Logger.info("Http endpoint successfully stated");
    }

    private static void runScheduler() {
        PeriodicTaskRunner.getInstance().run(
                new CertificateCollector(
                        certificates -> certificates.forEach(
                                (key, value) ->
                                        PrometheusCertificateExporter.getInstance()
                                                .measureCertificateExpiry(key, value)
                        )
                )
        );
    }
}
