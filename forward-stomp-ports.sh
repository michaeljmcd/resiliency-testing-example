#!/bin/sh

export NS=resiliency-testing-example

kubectl -n $NS port-forward pod/mq-broker-0 8161:8161 & 
kubectl -n $NS port-forward pod/mq-broker-1 8162:8161 & 
kubectl -n $NS port-forward pod/mq-broker-0 61613:61613 &
kubectl -n $NS port-forward pod/mq-broker-1 61614:61613
