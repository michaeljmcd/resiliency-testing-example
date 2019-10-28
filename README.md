# resiliency-testing-example

This is a a toy application that demonstrates using JMeter + Chaos Toolkit for
resiliency testing in a Kubernetes-based environment.

## The Scenario

## Prerequisites

Most of the work occurs in the cluster, but to get everything running you will
need the tools listed below.

* Docker
* A local Kubernetes cluster (either Docker Desktop or Minikube should work)
* Java 8+ & Maven to build the test containers
* Chaos Toolkit for running chaos tests
* JMeter to open or edit the load tests

## Building

The code for the example-specific containers described in [The Scenario](#the-scenario) is available 
under the `src` subtree. To build all of the images, run:

    sh build-docker-images.sh

This will build, tag and push all of the images to the local Docker registry so
that they will be available to Kubernetes. If you don't already have one running
at `localhost:5000` you can start it with the command below.

    docker run -d -p 5000:5000 --restart=always --name registry registry:2

## Running Tests

## License

MIT Licensed (See License.md), Michael McDermott, 2019.
