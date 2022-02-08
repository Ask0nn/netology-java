FROM openjdk:11.0.11-9-jre-slim
EXPOSE 5500
ADD build/libs/money-transfer-0.0.1.jar money-transfer.jar
ENTRYPOINT ["java", "-jar", "/money-transfer.jar"]