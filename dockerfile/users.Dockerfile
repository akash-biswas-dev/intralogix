FROM maven:3.9.12-eclipse-temurin-21-alpine AS builder

WORKDIR /app

COPY . .

RUN mvn -pl users -am clean package -DskipTests


FROM eclipse-temurin:21-jre-alpine-3.23

WORKDIR /app

COPY --from=builder /app/users/target/*.jar app.jar

EXPOSE 8501

ENTRYPOINT [ "java", "-jar","app.jar"]