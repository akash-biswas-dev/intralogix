# Builder image
FROM maven:3.9.12-eclipse-temurin-21-alpine AS builder

# Setup env install dependencies.
WORKDIR /proto-gen-java

COPY proto-gen-java .

RUN mvn clean install -DskipTests

WORKDIR /common-java

COPY common-java .

RUN mvn clean install -DskipTests

WORKDIR /app

# Copy all the files from users.
COPY access-manager .

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine-3.23

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8501

ENTRYPOINT [ "java", "-jar","app.jar"]
