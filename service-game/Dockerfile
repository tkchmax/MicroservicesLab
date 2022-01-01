FROM openjdk:17-alpine

WORKDIR /opt/server
COPY ./target/service-game-0.0.1-SNAPSHOT.jar server-game.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "server-game.jar"]