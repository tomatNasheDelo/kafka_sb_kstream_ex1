FROM gradle:8.12-jdk17 AS build
WORKDIR /workspace
COPY . .
RUN gradle :stream-processor:bootJar --no-daemon

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /workspace/stream-processor/build/libs/stream-processor-0.1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
