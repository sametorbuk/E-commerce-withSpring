FROM openjdk:17-jdk-alpine AS build


RUN apk add --no-cache maven

WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests


FROM openjdk:17-jdk-alpine
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Dserver.address=0.0.0.0", "-jar", "/app.jar"]

