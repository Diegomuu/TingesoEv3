FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} reservas.jar
ENTRYPOINT ["java", "-jar","/reservas.jar"]