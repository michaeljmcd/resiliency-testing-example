#!/bin/sh

cd src/amq-consumer
mvn install -DskipTests && docker build . -t localhost:5000/amq-consumer && docker push localhost:5000/amq-consumer
cd ../identity-svc
mvn install -DskipTests && docker build . -t localhost:5000/identity-svc && docker push localhost:5000/identity-svc
cd ../test-repo/load-tests
docker build . -t localhost:5000/load-tests && docker push localhost:5000/load-tests
