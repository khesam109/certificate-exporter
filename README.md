docker image build -t docker-java-jar:latest .

docker run -d -p 1234:1234 docker-java-jar:latest

docker run -d -e EXPORTER_CONFIG_PATH='/home/config.yml' -p 1234:1234 docker-java-jar:latest 