FROM openjdk:17-jdk-slim
VOLUME /tmp
ARG JAR_FILE=target/calentalk.jar
COPY ${JAR_FILE} calentalk.jar
ENTRYPOINT ["java", "-jar", "/calentalk.jar"]