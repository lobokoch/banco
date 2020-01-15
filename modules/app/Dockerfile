FROM adoptopenjdk/openjdk8-openj9:latest
VOLUME /tmp
COPY target/*.jar cadastros-banco-app.jar
ENTRYPOINT ["java", "-Xshareclasses:name=kerubin", "-jar", "/cadastros-banco-app.jar"]