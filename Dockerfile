# Description: Dockerfile for the Spring Boot application
# Set up without mvn package
# Run docker build --network=host -t api-service . to build the docker image
# Run docker run -p 8080:8080 napas-service to run the docker container

# Use the official maven/Java 21 image to create a build artifact.
FROM openjdk:21-jdk-slim AS MAVEN_BUILD

# Install Maven and other necessary tools
RUN apt-get update \
  && apt-get install -y maven ca-certificates curl git openssh-client --no-install-recommends \
  && rm -rf /var/lib/apt/lists/*

# Copy the pom.xml file to the container
COPY pom.xml /build/

# Copy the source code to the container
COPY src /build/src/
COPY .env /build/.env

# Set the working directory
WORKDIR /build/

# Package the application
RUN mvn clean package -DskipTests


# Check the target directory to verify the jar file
RUN ls -al target/


# Use the official OpenJDK 11 image to create a runtime image
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built jar file from the build stage
COPY --from=MAVEN_BUILD /build/target/*-SNAPSHOT.jar /app/app.jar

# Copy the .env file
COPY --from=MAVEN_BUILD /build/.env /app/.env




# Run the application
CMD ["java", "-jar", "/app/app.jar"]

# Expose the port
EXPOSE 8083

