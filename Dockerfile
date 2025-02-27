FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY target/concursomatch-1.0.0.jar concursomatch-1.0.0.jar
EXPOSE 8080
CMD ["java", "-jar", "concursomatch-1.0.0.jar"]