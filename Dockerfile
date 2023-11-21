FROM openjdk:21
COPY ./build/libs/hip94-0.0.1-SNAPSHOT.jar hip94.jar
ENTRYPOINT ["java","-Xmx8G","-Xms8G","-jar","/hip94.jar"]
