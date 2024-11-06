# OpenJDK 17 imajını kullanıyoruz (Spring Boot projeleri için uygun bir seçim)
FROM openjdk:17-jdk-alpine

# Uygulama jar dosyasını imajın içine kopyalayın
ARG JAR_FILE=target/ecommerce-backend-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# Uygulamayı başlat
ENTRYPOINT ["java", "-jar", "/app.jar"]
