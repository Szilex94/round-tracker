FROM openjdk:11
LABEL Name=round-tracker
COPY target/round-tracker-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]