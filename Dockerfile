# FROM eclipse-temurin:17-jdk-alpine
#
# WORKDIR /app
#
# COPY target/*.jar app.jar
#
# EXPOSE 8080
#
# ENTRYPOINT ["java","-jar","app.jar"]
#
#--------------------------------------------

# Stage 1: Build
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN apk add --no-cache maven && mvn clean package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

COPY --from=build /app/target/backend-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]