# Maven ile projenizi build edin
FROM maven:3.8.8-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Derlenen jar dosyasını çalışma ortamına taşıyın
FROM openjdk:17-jdk-alpine
COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
