FROM openjdk:21-jdk
WORKDIR /app
COPY target/im.db.api-0.0.1-SNAPSHOT.jar dbService.jar
CMD ["java", "-jar", "dbService.jar"]