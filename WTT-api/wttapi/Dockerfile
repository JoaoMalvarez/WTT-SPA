FROM maven:3.9-eclipse-temurin-21 as builder

WORKDIR /app

# 1. Copy pom first to leverage Docker cache for dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# 2. NOW copy the source code
COPY src ./src

# 3. Build the actual application
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# Copy the generated JAR from the target folder
COPY --from=builder /app/target/wtt*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
