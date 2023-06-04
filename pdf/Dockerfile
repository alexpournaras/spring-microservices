FROM openjdk:8-jdk-alpine

RUN apk add --no-cache maven

COPY . /pdf

WORKDIR /pdf

RUN mvn clean install

RUN mv target/*.jar pdf.jar

EXPOSE 3333

CMD ["/usr/bin/java", "-jar", "pdf.jar", "--server.port=3333", "pdf"]
