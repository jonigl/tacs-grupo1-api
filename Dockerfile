#sudo docker run -it jonigl/tacs-grupo1-api:1.0.0
FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY eventbrite.yml eventbrite.yml
COPY telegram.yml telegram.yml
COPY jwt.yml jwt.yml
COPY target target
ENTRYPOINT ["java","-Dspring.data.mongodb.uri=mongodb://mongodb:27017/eventapp","-jar","target/event-app-1.0.0-SNAPSHOT.jar"]
