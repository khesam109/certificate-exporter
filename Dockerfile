FROM eclipse-temurin:17-jdk-alpine
COPY src/main/resources/* /etc/certificateexporter/
COPY target/prom-certificate-exporter-1.1.0-jar-with-dependencies.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]