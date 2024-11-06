FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/ecommmerce-backend-0.0.1-SNAPSHOT.jar /app/
ENTRYPOINT ["java", "-jar", "ecommmerce-backend-0.0.1-SNAPSHOT.jar"]









    