FROM openjdk:12-jdk-alpine
COPY ./build/libs/etl-0.0.1-SNAPSHOT.jar hip94.jar
ENTRYPOINT ["java","-Xmx8G","-Xms8G","-jar","/hip94.jar"]
