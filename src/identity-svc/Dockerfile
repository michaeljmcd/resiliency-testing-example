FROM azul/zulu-openjdk:11
RUN mkdir /app
COPY target/identity-svc-0.0.1-SNAPSHOT.jar /app
ENTRYPOINT java -jar /app/identity*.jar
