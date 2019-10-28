package com.example.amqconsumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.StringReader;

@Component
@Slf4j
public class IdentityProcessor implements Processor {
    @Autowired
    public IdentityProcessor() {
    }

    @Override
    public void process(final Exchange exchange) throws Exception {
        log.debug("Received message.");

        final String messageBody = exchange.getIn().getBody(String.class);

        if (messageBody == null) {
            return;
        }

        log.info("Received message {}", messageBody);
    }
}
