FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} descuentosFrecuencia.jar
ENTRYPOINT ["java", "-jar","/descuentosFrecuencia.jar"]