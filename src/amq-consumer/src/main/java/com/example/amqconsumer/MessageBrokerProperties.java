package com.example.amqconsumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties
public class MessageBrokerProperties { 
	@Value("${brokerURL}")
	private String consumerBrokerUrl;

	@Value("${maximumRedeliveryDelay}")
	private int maximumRedeliveryDelay;

	@Value("${maximumRedeliveries}")
	private int maximumRedeliveries;

	@Value("${maxConnections}")
	private int maxConnections;

	@Value("${concurrentConsumers}")
	private int concurrentConsumers;

	@Value("${backOffMultiplier}")
	private int backOffMultiplier;
}
