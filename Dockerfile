FROM openjdk:17
WORKDIR /app
RUN mvn clean package -DskipTests
COPY target/final-project-01-webservice-0.0.1-SNAPSHOT.jar /app/finpro-webservice.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","finpro-webservice.jar"]