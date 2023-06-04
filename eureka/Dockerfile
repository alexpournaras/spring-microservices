FROM openjdk:8-jdk-alpine

RUN apk add --no-cache maven

COPY . /eureka

WORKDIR /eureka

RUN mvn clean install

RUN mv target/*.jar eureka.jar

EXPOSE 8761

CMD ["/usr/bin/java", "-jar", "eureka.jar", "--server.port=8761", "eureka"]
