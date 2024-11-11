# OpenJDK 17 kullanarak Maven'ı yükleyin
FROM openjdk:17-jdk-alpine AS build

# Maven'ı yükleyin
RUN apk add --no-cache maven

WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Derlenen jar dosyasını taşıyın
FROM openjdk:17-jdk-alpine
COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
