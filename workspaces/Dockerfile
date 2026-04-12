FROM eclipse-temurin:21-jre-alpine-3.23

WORKDIR /app

COPY workspaces/target/workspaces.jar app.jar

EXPOSE 8502

ENTRYPOINT [ "java", "-jar","app.jar"]