FROM openjdk:21-jdk-oracle
WORKDIR /app
COPY /out/artifacts/wildberries_parser_jar/wildberries-parser.jar /app/wb-parser.jar
ENTRYPOINT ["java", "-jar", "wb-parser.jar"]