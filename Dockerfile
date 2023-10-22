FROM adoptopenjdk:17-jre-hotspot

WORKDIR /app

COPY target/ChatDiary-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=production

CMD ["java", "-jar", "app.jar","--spring.profiles.active=$SPRING_PROFILES_ACTIVE"]
