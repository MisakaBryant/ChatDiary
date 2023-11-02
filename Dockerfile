FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/ChatDiary-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=product

CMD ["java", "-jar", "app.jar","--spring.profiles.active=docker"]
