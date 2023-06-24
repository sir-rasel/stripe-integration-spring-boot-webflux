FROM openjdk:17-jdk-slim

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:resolve

COPY src ./src
EXPOSE 8080

CMD ["./mvnw", "spring-boot:run"]