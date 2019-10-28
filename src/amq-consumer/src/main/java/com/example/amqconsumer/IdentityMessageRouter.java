package com.example.amqconsumer;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class IdentityMessageRouter extends RouteBuilder {
    private final String subscriptionDestination;
    private final IdentityProcessor positionProcessor;

    @Autowired
    public IdentityMessageRouter(final @Value("${message-topic}") String subscriptionDestination,
            final IdentityProcessor positionProcessor) {
        this.subscriptionDestination = subscriptionDestination;
        this.positionProcessor = positionProcessor;
    }

    @Override
    public void configure() throws Exception {
        from(subscriptionDestination).process(positionProcessor);
    }
}
