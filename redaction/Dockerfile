FROM openjdk:8-jdk-alpine

RUN apk add --no-cache maven

COPY . /redaction

WORKDIR /redaction

RUN mvn clean install

RUN mv target/*.jar redaction.jar

EXPOSE 2222

CMD ["/usr/bin/java", "-jar", "redaction.jar", "--server.port=2222", "redaction"]