FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} tarifasFestivos.jar
ENTRYPOINT ["java", "-jar","/tarifasFestivos.jar"]