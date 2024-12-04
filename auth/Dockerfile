FROM openjdk:22-jdk-slim

EXPOSE 8080

RUN mkdir /auth
WORKDIR /auth

ENTRYPOINT ["java", "-jar", "spring-boot-application.jar"]