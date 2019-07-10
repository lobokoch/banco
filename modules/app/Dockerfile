FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/*.jar cadastros-banco-app.jar
ENTRYPOINT ["java","-jar","/cadastros-banco-app.jar"]