FROM openjdk:17-alpine

WORKDIR /opt/server
COPY ./target/service-claims-0.0.1-SNAPSHOT.jar server-claims.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "server-claims.jar"]