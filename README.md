# RSO: Product microservice

## Prerequisites

```bash
docker run -d --name pg-product -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=product -p 5432:5432 postgres:13
```

## Build and run commands
```bash
mvn clean package
cd api/target
java -jar product-api-1.0.0-SNAPSHOT.jar
```
Available at: localhost:8080/v1/products

## Run in IntelliJ IDEA
Add new Run configuration and select the Application type. In the next step, select the module api and for the main class com.kumuluz.ee.EeApplication.

Available at: localhost:8080/v1/products

## Docker commands
```bash
docker build -t novaslika .   
docker images
docker run novaslika    
docker tag novaslika prporso/novaslika   
docker push prporso/novaslika
docker ps
```



