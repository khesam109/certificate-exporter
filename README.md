docker image build -t docker-java-jar:latest .

docker run -d -p 1234:1234 docker-java-jar:latest