package com.example.amqconsumer;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import javax.jms.ConnectionFactory;

@Configuration
public class MessageBrokerConfiguration {
	private final MessageBrokerProperties automatedAppointmentActiveMQProperties;

	@Autowired
	public MessageBrokerConfiguration(MessageBrokerProperties automatedAppointmentActiveMQProperties) {
		this.automatedAppointmentActiveMQProperties = automatedAppointmentActiveMQProperties;
	}

    @Bean
    public ConnectionFactory activeMQConnectionFactory(final @Value("${SERVICE_UID}") String serviceUid, 
            final @Value("${SERVICE_PASSWORD}") String servicePassword) {
        final ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
		activeMQConnectionFactory.setBrokerURL(automatedAppointmentActiveMQProperties.getConsumerBrokerUrl());
		activeMQConnectionFactory.setUserName(serviceUid);
		activeMQConnectionFactory.setPassword(servicePassword);
		activeMQConnectionFactory.setRedeliveryPolicy(redeliveryPolicy());

		PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
		pooledConnectionFactory.setConnectionFactory(activeMQConnectionFactory);
		pooledConnectionFactory.setMaxConnections(automatedAppointmentActiveMQProperties.getMaxConnections());

        return pooledConnectionFactory;
    }

	@Bean
	public ActiveMQComponent activeMQConsumer(final ConnectionFactory pooledConnectionFactory) {
		ActiveMQComponent activeMQComponent = new ActiveMQComponent();
		activeMQComponent.setConnectionFactory(pooledConnectionFactory);
		activeMQComponent.setConcurrentConsumers(automatedAppointmentActiveMQProperties.getConcurrentConsumers());
		activeMQComponent.setCacheLevel(DefaultMessageListenerContainer.CACHE_CONSUMER);
		activeMQComponent.setTransacted(false);

		return activeMQComponent;
	}

	private RedeliveryPolicy redeliveryPolicy() {
		RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
		redeliveryPolicy.setBackOffMultiplier(automatedAppointmentActiveMQProperties.getBackOffMultiplier());
		redeliveryPolicy.setUseExponentialBackOff(true);
		redeliveryPolicy.setMaximumRedeliveryDelay(automatedAppointmentActiveMQProperties.getMaximumRedeliveryDelay());
		redeliveryPolicy.setMaximumRedeliveries(automatedAppointmentActiveMQProperties.getMaximumRedeliveries());
		return redeliveryPolicy;
	}
}
