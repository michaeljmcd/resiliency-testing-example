package com.example.amqconsumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.StringReader;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class IdentityProcessor implements Processor {
    final RestTemplate restTemplate;

    @Autowired
    public IdentityProcessor(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void process(final Exchange exchange) throws Exception {
        log.debug("Received message.");

        final String messageBody = exchange.getIn().getBody(String.class);

        if (messageBody == null) {
            return;
        }

        log.info("Received message {}", messageBody);

        final String response = restTemplate.postForObject("/identity", messageBody, String.class);

        log.info("Got answer {}", response);
        log.info("And that's all she wrote.");
    }
}
