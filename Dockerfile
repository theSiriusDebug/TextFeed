FROM openjdk:21
ADD /target/TextFeed-0.0.1-SNAPSHOT.jar backend.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "backend.jar"]