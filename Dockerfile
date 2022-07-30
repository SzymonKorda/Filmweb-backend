FROM maven:3.8.3-jdk-8
WORKDIR /app
COPY src /app/src
COPY pom.xml /app
RUN mvn -f /app/pom.xml clean package -DskipTests
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "target/filmweb-0.0.1-SNAPSHOT.jar"]
