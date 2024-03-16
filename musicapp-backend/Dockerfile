# Build application
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn clean package

# Create the final Docker image
FROM openjdk:17-jdk-slim
WORKDIR /app
# Copy the built .jar file from the previous stage
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
