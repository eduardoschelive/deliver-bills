FROM amazoncorretto:21.0.4-alpine3.20
LABEL maintainer="Eduardo Guiraldelli Schelive"
COPY target/deliver-bills-0.0.1.jar /opt/deliver-bills/server.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/opt/deliver-bills/server.jar"]