FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} clientes.jar
ENTRYPOINT ["java", "-jar","/clientes.jar"]