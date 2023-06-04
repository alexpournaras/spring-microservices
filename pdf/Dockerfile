FROM openjdk:8-jdk-alpine

RUN apk add --no-cache maven

COPY . /pdf

WORKDIR /pdf

RUN mvn clean install

EXPOSE 3333

CMD ["java", "-jar", "target/pdf-service-v1.0.0.jar"]