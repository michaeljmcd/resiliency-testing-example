# resiliency-testing-example

This is a a toy application that demonstrates using JMeter + Chaos Toolkit for
resiliency testing in a Kubernetes-based environment.

## The Scenario

The basic solution deployed into Kubernetes looks like this:

    |ActiveMQ Broker (x2)| --(Q)-> [Processor]
                                       |
                                       v
                                    [Wiremock]
                                       |
                                       v
                                    [Simple Web Service]

The web service in question simply echoes back its input body and functions
merely to illustrate dependencies. The two ActiveMQ brokers use a shared file
system and will failover if the active node is terminated.

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

To run the tests, take the following steps from the root of this repository:

    export NS=resiliency-testing-example

    # Build the docker images:
    sh build-docker-images.sh

    # Deploy the resources to a local Kubernetes cluster:
    kubectl -n $NS apply -f manifest.yml

    # Wait for the resources to be created...
    # Verify with:
    kubectl -n $NS get all

    # Next run the load tests that tinker with Wiremock:
    cd src/test-repo
    kubectl -n $NS apply -f manifest.yml
    # We can check the status of the job like so:
    kubectl -n $NS get job --watch
    # Once the job completes, we can view the test results from the mount point.

    # Finally run the chaos experiment
    cd ../failure-tests

    # Forward ports needed for the test:
    kubectl -n $NS port-forward pod/mq-broker-0 8161:8161 & 
    kubectl -n $NS port-forward pod/mq-broker-1 8162:8161 & 
    kubectl -n $NS port-forward pod/mq-broker-0 61613:61613 &
    kubectl -n $NS port-forward pod/mq-broker-1 61614:61613 &

    # Run the experiment:
    PYTHONPATH="$PYTHONPATH:." chaos --verbose run broker-failover.json

Beyond this, the whole app is a sandbox. Have fun!

## License

MIT Licensed (See LICENSE.md), Michael McDermott, 2019.
