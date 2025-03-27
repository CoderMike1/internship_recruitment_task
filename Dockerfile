FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src/main ./src/main
RUN mvn clean package -DskipTests

RUN mvn test

FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY --from=build /app/target/remitlyRecruitmentTask-0.0.1-SNAPSHOT.jar app.jar


EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]