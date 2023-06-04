FROM openjdk:8-jdk-alpine

RUN apk add --no-cache maven

COPY . /eureka

WORKDIR /eureka

RUN mvn clean install

EXPOSE 8761

CMD ["java", "-jar", "target/eureka-service-v1.0.0.jar"]