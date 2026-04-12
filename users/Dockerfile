FROM eclipse-temurin:21-jre-alpine-3.23

WORKDIR /app

COPY users/target/users.jar app.jar

EXPOSE 8501

ENTRYPOINT [ "java", "-jar","app.jar"]
