FROM openjdk:21-jdk-oracle
WORKDIR /app
COPY /target/wildberries-parser-0.0.1-SNAPSHOT.jar /app/parser.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "parser.jar"]