# Build stage
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:resolve
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Set environment variables
ENV PORT=10000
ENV JAVA_OPTS="-Xmx512m -Xms256m"

EXPOSE 10000

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
