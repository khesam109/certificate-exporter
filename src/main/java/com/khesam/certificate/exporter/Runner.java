package com.khesam.certificate.exporter;

import com.khesam.certificate.exporter.collector.CertificateCollector;
import com.khesam.certificate.exporter.config.ConfigReader;
import com.khesam.certificate.exporter.scheduler.CertificateCollectorCallback;
import com.khesam.certificate.exporter.scheduler.PeriodicTaskRunner;
import io.prometheus.client.Counter;
import io.prometheus.client.exporter.HTTPServer;

import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.Collection;

public class Runner {

    static final Counter requests = Counter.build()
            .name("requests_total").help("Total requests.").register();

    static void processRequest() {
        requests.inc();
        System.out.println("salam");
        // Your code here.
    }

    public static void main(String[] args) throws IOException {
        HTTPServer server = new HTTPServer.Builder()
                .withPort(1234)
                .build();
//        ExporterConfig.directories.add(new CertificateDirectory("D:\\Project\\Personal\\certificate-exporter1\\prom-certificate-exporter\\src\\main\\resources", List.of("crt")));
        ConfigReader.init("D:\\Project\\Personal\\certificate-exporter\\src\\main\\resources\\config.yml");
        PeriodicTaskRunner.getInstance().run(
                new CertificateCollector(new CertificateCollectorCallback() {
                    @Override
                    public void onSuccess(Collection<X509Certificate> certificates) {
                        certificates.forEach(e -> System.out.println(e.getNotAfter().toString()));
                    }

                    @Override
                    public void onFiled(String message) {

                    }
                })
        );


//        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
//        scheduler.scheduleAtFixedRate(new Runnable() {
//            public void run() {
//                processRequest();
//
//            }
//        }, 0, 1, TimeUnit.SECONDS);
    }
}
