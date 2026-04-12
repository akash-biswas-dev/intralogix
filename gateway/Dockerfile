FROM eclipse-temurin:21-jre-alpine-3.23

WORKDIR /app

COPY gateway/target/gateway.jar app.jar

EXPOSE 9000

ENTRYPOINT [ "java", "-jar","app.jar"]
