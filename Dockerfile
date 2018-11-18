FROM maven:3.3.9-jdk-8-alpine AS builder
WORKDIR /build
COPY . .
RUN mvn -am --quiet package

FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY eventbrite.yml eventbrite.yml
COPY telegram.yml telegram.yml
COPY jwt.yml jwt.yml
COPY --from=builder /build/target target
ENTRYPOINT ["java","-Dspring.data.mongodb.uri=mongodb://admin:admin123@ds161913.mlab.com:61913/tacs-grupo1","-jar","target/event-app-1.0.0.jar"]
