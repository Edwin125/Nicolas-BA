# Use an official OpenJDK runtime as a parent image
FROM amazoncorretto:21-alpine-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the application JAR file to the container
ARG JAR_FILE=build/libs/webchatbot-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /app/webchatbot.jar

# Expose the port the app runs on
EXPOSE 8080

# Define environment variables
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5433/ukb \
    SPRING_DATASOURCE_USERNAME=docker_user \
    SPRING_DATASOURCE_PASSWORD=docker_user

# Run the jar file
ENTRYPOINT ["java","-jar","webchatbot.jar"]