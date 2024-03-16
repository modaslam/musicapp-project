# Base image containing Java runtime
FROM openjdk:17-jdk-slim as build

LABEL maintainer="modaslam1327@gmail.com"

VOLUME /tmp

EXPOSE 8080

ARG JAR_FILE=target/*.jar

# Add jar to the container
ADD ${JAR_FILE} app.jar

# Run the jar file
ENTRYPOINT ["java","-jar","/app.jar"]
