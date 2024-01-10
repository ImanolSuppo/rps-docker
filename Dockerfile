FROM eclipse-temurin:17-jdk-alpine
ARG Jar_File=target/*.jar
COPY ${Jar_File} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]