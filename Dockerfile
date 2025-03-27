FROM maven:3.8.5-eclipse-temurin-17 AS build

WORKDIR /app


COPY src ./src
COPY pom.xml .

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine

ADD target/remitlyRecruitmentTask-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]