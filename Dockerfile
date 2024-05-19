FROM openjdk:17
WORKDIR /app
EXPOSE 8080
COPY target/final-project-01-webservice-0.0.1-SNAPSHOT.jar finpro-webservice.jar
ENTRYPOINT ["java","-jar","/finpro-webservice.jar"]