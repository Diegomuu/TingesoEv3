FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} descuentoGrupo.jar
ENTRYPOINT ["java", "-jar","/descuentoGrupo.jar"]