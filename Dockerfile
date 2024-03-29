#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM openjdk:8-jre-slim
COPY --from=build /home/app/target/app.jar /usr/local/lib/demo.jar
COPY --from=build /home/app/src/main/resources/config.properties config.properties
EXPOSE 8081
ENTRYPOINT ["java","-jar","/usr/local/lib/demo.jar"]


