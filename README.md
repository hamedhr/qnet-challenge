# Quant Network

## Project Structure

`/src/*` structure follows default Java structure.

## Development

1. chmod +x create_dir.sh
2. ./create_dir.sh
3. docker-compose -f docker-compose.yaml up -d
4. docker ps -a (to verify)

Rest API default port: 8080

#### Health check endpoint

`BASE_URL:8080/actuator/health`

#### For Development

Please consider passing below parameters with proper value:

Environment Variables)

`PORT.....`

- spring.application.name=my_oam
- MANAGEMENT_SERVER_PORT=8380

#### Building runnable jar file

`mvn clean install`

#### Building Docker image

`docker build -t spring-boot-template-service-.....:0.0.1 .`

#### Running Docker image

`docker run -d --name irex-oam -p 8080:8080 -e SPRING_PROFILES_ACTIVE=staging -e PG_USER=pg_username -e PG_PASSWORD=pg_password -e PG_HOST=localhost -e PG_PORT=5432 -e PG_DB=db_name -e KAFKA_SERVERS=127.0.0.1:9092 -e scheduled.job.trade-commission=0 0 0 * * ? irex-oam:0.0.1 -p 8080:8080`

Example:

`docker run -d --name irex-oam -p 8080:8080             
-e SPRING_PROFILES_ACTIVE=staging -e PG_USER=postgres -e PG_PASSWORD=R@@t -e PG_HOST=192.168.1.6 -e PG_PORT=5432 -e PG_DB=testdb -e KAFKA_SERVERS=127.0.0.1:9092 -e scheduled.job.trade-commission=0 0 0 * * ?           
-t irex-oam:0.0.1 -e ENV_PATH=/opt/app/.env`

### Resources:

[mongodb](https://www.mongodb.com/docs/drivers/reactive-streams/)

[mongodb](https://www.mongodb.com/docs/drivers/reactive-streams/)