@echo off
echo Aplicando ConfigMap y Secret...
kubectl apply -f config/database-configmap.yml --validate=false
kubectl apply -f config/database-secret.yml --validate=false

echo Esperando 5 segundos...
timeout /t 5

echo Desplegando Config Server...
kubectl apply -f configServer-deployment-service.yml --validate=false
echo Esperando 30 segundos...
timeout /t 80

echo Desplegando Eureka Server...
kubectl apply -f eureka-deployment-service.yml --validate=false
echo Esperando 30 segundos...
timeout /t 30

echo Desplegando API Gateway...
kubectl apply -f gateway-deployment-service.yml --validate=false
echo Esperando 30 segundos...
timeout /t 30

echo Desplegando bases de datos MySQL...
kubectl apply -f mysql-clientes-deployment.yml --validate=false
timeout /t 15
kubectl apply -f mysql-reservas-deployment.yml --validate=false
timeout /t 15
kubectl apply -f mysql-rack-deployment.yml --validate=false
timeout /t 15
kubectl apply -f mysql-tarifas-deployment.yml --validate=false
timeout /t 15
kubectl apply -f mysql-tarifas-festivos-deployment.yml --validate=false
timeout /t 15
kubectl apply -f mysql-descuentos-frecuencia-deployment.yml --validate=false
timeout /t 15
kubectl apply -f mysql-descuentos-grupo-deployment.yml --validate=false

echo Esperando que las bases de datos estén listas...
timeout /t 60

echo Desplegando microservicios...
kubectl apply -f clientes-deployment-service.yml --validate=false
timeout /t 15
kubectl apply -f reservas-deployment-service.yml --validate=false
timeout /t 15
kubectl apply -f rack-deployment-service.yml --validate=false
timeout /t 15
kubectl apply -f tarifas-deployment-service.yml --validate=false
timeout /t 15
kubectl apply -f tarifasFestivos-deployment-service.yml --validate=false
timeout /t 15
kubectl apply -f descuentosFrecuencia-deployment-service.yml --validate=false
timeout /t 15
kubectl apply -f descuentosGrupo-deployment-service.yml --validate=false

echo Despliegue completado. Verificando estado...
kubectl get pods

pause 