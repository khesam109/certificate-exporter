Certificate Exporter
==============
---
A Prometheus exporter for certificates focusing on expiration monitoring, written in Java.
`certificate-exporter`  will parse SSL certificates in a number of directories recursively and expose their expiry as a Prometheus metric.
It also can fetch remote endpoint certificate and parse it.


---

# docker

`docker image build -t docker-java-jar:latest .
`

`docker run -d -p 1234:1234 docker-java-jar:latest
`

`docker run -d -e EXPORTER_CONFIG_PATH='/home/config.yml' -p 1234:1234 docker-java-jar:latest 
`
---