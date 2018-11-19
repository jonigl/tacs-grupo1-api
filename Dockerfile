FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY eventbrite.yml eventbrite.yml
COPY telegram.yml telegram.yml
COPY jwt.yml jwt.yml
COPY target target
ENTRYPOINT ["java","-jar","target/event-app-1.0.0.jar"]
