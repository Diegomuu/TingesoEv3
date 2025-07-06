@echo off
echo Building and pushing Docker images for all microservices...

cd gateway
echo Building gateway service with Maven...
mvn clean package -DskipTests
echo Building and pushing Docker image for gateway...
docker build -t diegomuu/gateway .
docker push diegomuu/gateway
cd ..

cd eureka
echo Building eureka service with Maven...
mvn clean package -DskipTests
echo Building and pushing Docker image for eureka...
docker build -t diegomuu/eureka-server .
docker push diegomuu/eureka-server
cd ..

cd tarifasFestivos
echo Building tarifasFestivos service with Maven...
mvn clean package -DskipTests
echo Building and pushing Docker image for tarifasFestivos...
docker build -t diegomuu/tarifas-festivos-service .
docker push diegomuu/tarifas-festivos-service
cd ..

cd tarifas
echo Building tarifas service with Maven...
mvn clean package -DskipTests
echo Building and pushing Docker image for tarifas...
docker build -t diegomuu/tarifas-service .
docker push diegomuu/tarifas-service
cd ..

cd reservas
echo Building reservas service with Maven...
mvn clean package -DskipTests
echo Building and pushing Docker image for reservas...
docker build -t diegomuu/reservas-service .
docker push diegomuu/reservas-service
cd ..

cd rack
echo Building rack service with Maven...
mvn clean package -DskipTests
echo Building and pushing Docker image for rack...
docker build -t diegomuu/rack-service .
docker push diegomuu/rack-service
cd ..

cd descuentosGrupo
echo Building descuentosGrupo service with Maven...
mvn clean package -DskipTests
echo Building and pushing Docker image for descuentosGrupo...
docker build -t diegomuu/descuentos-grupo-service .
docker push diegomuu/descuentos-grupo-service
cd ..

cd descuentosFrecuencia
echo Building descuentosFrecuencia service with Maven...
mvn clean package -DskipTests
echo Building and pushing Docker image for descuentosFrecuencia...
docker build -t diegomuu/descuentos-frecuencia-service .
docker push diegomuu/descuentos-frecuencia-service
cd ..

cd clientes
echo Building clientes service with Maven...
mvn clean package -DskipTests
echo Building and pushing Docker image for clientes...
docker build -t diegomuu/clientes-service .
docker push diegomuu/clientes-service
cd ..

cd configServer
echo Building config server with Maven...
mvn clean package -DskipTests
echo Building and pushing Docker image for config server...
docker build -t diegomuu/config-server .
docker push diegomuu/config-server
cd ..

echo All services have been built and Docker images have been pushed successfully!
pause 