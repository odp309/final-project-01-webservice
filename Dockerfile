FROM openjdk:17
WORKDIR /app
COPY target/final-project-01-webservice-0.0.1-SNAPSHOT.jar /app/finpro-webservice.jar
ENTRYPOINT ["java","-jar","finpro-webservice.jar"]
EXPOSE 8080