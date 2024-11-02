FROM openjdk:21-jdk-oracle
WORKDIR /app
COPY /target/*.jar /app/parser.jar
ENTRYPOINT ["java", "-jar", "parser.jar"]