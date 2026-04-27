FROM eclipse-temurin:21-jre-alpine-3.23

WORKDIR /app

COPY services/access-manager/target/access-manager.jar app.jar

EXPOSE 8503

ENTRYPOINT [ "java", "-jar","app.jar"]
