FROM openjdk:8-jdk-alpine
EXPOSE 8081
ADD build/libs/cloud-vault-0.0.1-SNAPSHOT.jar Cloud-Vault.jar
ENTRYPOINT ["java", "-jar", "/Cloud-Vault.jar"]