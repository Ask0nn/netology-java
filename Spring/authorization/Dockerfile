FROM openjdk:11.0.11-9-jre-slim

EXPOSE 8080

ADD build/libs/authorization-0.0.1.jar AuthService.jar

ENTRYPOINT ["java", "-jar", "/AuthService.jar"]