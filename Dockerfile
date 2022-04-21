FROM adoptopenjdk/maven-openjdk11:latest AS build
WORKDIR /opt/app
COPY . .
RUN mvn clean install
FROM adoptopenjdk/openjdk11:alpine-jre
WORKDIR /opt/app
COPY  --from=build   /opt/app/target/spring-boot-template-service-*-SNAPSHOT.jar ./app.jar

ENV JAVA_OPTS="-Xms1024m  -Xmx2048m"
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"] 
