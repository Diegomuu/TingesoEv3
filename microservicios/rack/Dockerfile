FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} rack.jar
ENTRYPOINT ["java", "-jar","/rack.jar"]