FROM openjdk:21-jdk-oracle
WORKDIR /app
COPY /target/wb-parser.jar /app/parser.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "parser.jar"]