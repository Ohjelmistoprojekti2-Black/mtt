FROM maven:3.8.7-openjdk-18-slim AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:22-jdk-slim
ENV SPRING_CONFIG_NAME=application,production
COPY --from=build /target/op2-0.0.1-SNAPSHOT.jar mtt.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","mtt.jar"]