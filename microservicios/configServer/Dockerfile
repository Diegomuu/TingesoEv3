FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} configServer.jar
ENTRYPOINT ["java", "-jar","/configServer.jar"]