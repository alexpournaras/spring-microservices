FROM openjdk:8-jdk-alpine

RUN apk add --no-cache maven

COPY . /redaction

WORKDIR /redaction

RUN mvn clean install

EXPOSE 2222

CMD ["java", "-jar", "target/redaction-service-v1.0.0.jar"]