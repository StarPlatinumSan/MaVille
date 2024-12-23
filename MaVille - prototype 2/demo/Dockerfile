FROM eclipse-temurin:17-jdk

# Set the working directory
WORKDIR /app

# Copy the project files
COPY . /app

# Ensure Maven wrapper has execution permissions
RUN chmod +x mvnw

# Build the project using Maven
RUN ./mvnw clean install -DskipTests

# Expose the application's port
EXPOSE 8080

# Run the Spring Boot application
CMD ["java", "-jar", "target/*.jar"]

