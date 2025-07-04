FROM openjdk:20
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} KartingBack.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar","KartingBack.jar"]