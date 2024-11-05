# Base image olarak OpenJDK kullan
FROM openjdk:17-jdk-slim

# JAR dosyasını /app klasörüne kopyala
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Spring Boot uygulamasını başlat
ENTRYPOINT ["java", "-jar", "/app.jar"]

# Uygulamanın çalışacağı port
EXPOSE 8080











    