FROM azul/zulu-openjdk:11
RUN mkdir /app
COPY target/amq-consumer-0.0.1-SNAPSHOT.jar /app
ENTRYPOINT java -jar /app/amq*.jar
