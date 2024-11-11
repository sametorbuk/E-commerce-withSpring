FROM openjdk:17-jdk-alpine AS build

# Maven'ı yükleyin
RUN apk add --no-cache maven

WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Derlenen jar dosyasını taşıyın
FROM openjdk:17-jdk-alpine
COPY --from=build /app/target/*.jar app.jar

# Dışarıya port 8080 açılıyor
EXPOSE 8080

ENTRYPOINT ["java", "-Dserver.address=0.0.0.0", "-jar", "/app.jar"]

