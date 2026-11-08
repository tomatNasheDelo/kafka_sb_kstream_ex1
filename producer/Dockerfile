FROM gradle:8.12-jdk17 AS build
WORKDIR /workspace
COPY . .
RUN gradle :producer:bootJar --no-daemon

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /workspace/producer/build/libs/producer-0.1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
