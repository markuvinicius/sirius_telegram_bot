package com.markuvinicius.configuration;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class ArtemisConfiguration {

    @Value("${artemis.broker-url}")
    private String brokerUrl;

    @Value("${artemis.username")
    private String userName;

    @Value("${artemis.password}")
    private String password;

    @Bean
    public ActiveMQConnectionFactory senderActiveMQConnectionFactory() {
        return new ActiveMQConnectionFactory(brokerUrl)
                .setUser(userName)
                .setPassword(password);
    }

    @Bean
    public CachingConnectionFactory cachingConnectionFactory() {
        return new CachingConnectionFactory(senderActiveMQConnectionFactory());
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        return new JmsTemplate(cachingConnectionFactory());
    }
}
