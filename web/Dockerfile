FROM openjdk:8-jdk-alpine

RUN apk add --no-cache maven

COPY . /web

WORKDIR /web

RUN mvn clean install

EXPOSE 4444

CMD ["java", "-jar", "target/web-service-v1.0.0.jar"]