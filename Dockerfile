FROM openjdk:21-slim
WORKDIR /app
ADD target/*.jar /app/app.jar
EXPOSE 8080
USER 1001
ENTRYPOINT ["java", "-jar", "app.jar"]