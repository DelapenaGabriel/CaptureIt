# Build stage
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
# Download dependencies first for better caching
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Standard Spring Boot environment variable defaults
ENV SERVER_PORT=9000
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://aws-1-us-east-1.pooler.supabase.com:6543/postgres
ENV SPRING_DATASOURCE_USERNAME=postgres.zxdwcyshzxkrzraolmmh
ENV SPRING_DATASOURCE_PASSWORD=!Jonathanandnorma2026

EXPOSE 9000

ENTRYPOINT ["java", "-jar", "app.jar"]
