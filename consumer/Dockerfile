FROM gradle:8.12-jdk17 AS build
WORKDIR /workspace
COPY . .
RUN gradle :consumer:bootJar --no-daemon

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /workspace/consumer/build/libs/consumer-0.1.0-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
