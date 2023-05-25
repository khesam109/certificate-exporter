FROM openjdk:11
COPY target/prom-certificate-exporter-1.0.0-jar-with-dependencies.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]