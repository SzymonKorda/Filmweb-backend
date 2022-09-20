FROM maven:3.8.3-jdk-11-slim as build
WORKDIR /app
COPY . /app
RUN mvn clean package -DskipTests

FROM openjdk:11
COPY --from=build /app/target/filmweb-0.0.1-SNAPSHOT.jar /app/filmweb.jar
WORKDIR /app
EXPOSE 8080
CMD ["java", "-jar", "filmweb.jar"]
