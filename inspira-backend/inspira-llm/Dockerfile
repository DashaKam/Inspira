# Сборка приложения
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package spring-boot:repackage -DskipTests

# Запуск приложения
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/inspira-llm-1.0-SNAPSHOT.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]
