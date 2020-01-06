FROM openjdk:8-jdk-alpine
ARG profil
ENV profil=${profil}
COPY target/*.jar app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=${profil}", "-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]