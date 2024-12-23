FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copy project files and build the project
COPY . /app
RUN chmod +x mvnw
RUN ./mvnw clean install -DskipTests

# Expose the port
EXPOSE 8080

# Replace "your-app-name" with the actual name of the JAR file generated
CMD ["java", "-jar", "target/demo-0.0.1-SNAPSHOT.jar"]




