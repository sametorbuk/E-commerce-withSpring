# Base image olarak OpenJDK kullan
FROM openjdk:17-jdk-slim

# JAR dosyasını /app klasörüne kopyala
COPY target/ecommmerce-backend-0.0.1-SNAPSHOT.jar app.jar

# Spring Boot uygulamasını başlat
ENTRYPOINT ["java", "-jar", "/app.jar"]

# Uygulamanın çalışacağı port
EXPOSE 8080









    