FROM adoptopenjdk/openjdk11:alpine

USER 1001

COPY /target/mq-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080:8080

ENTRYPOINT ["java", "-jar", "app.jar"]