FROM openjdk:17-jdk-slim
VOLUME /tmp

ARG JAR_FILE=build/libs/calenTalk-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} calentalk.jar

ENTRYPOINT ["java", "-jar", "/calentalk.jar"]
