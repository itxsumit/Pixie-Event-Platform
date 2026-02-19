# Use Java 17 as base image
FROM eclipse-temurin:17-jdk-alpine
# Create app directory
WORKDIR /app
# Copy the built jar file
COPY target/*.jar app.jar
# Expose the port
EXPOSE 8080
# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]