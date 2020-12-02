package com.markuvinicius.services.implementation;

import com.markuvinicius.services.MessageSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageSenderServiceImpl implements MessageSenderService {

    private JmsTemplate jmsTemplate;

    @Value("${artemis.message-queue-name}")
    private String messageQueueName;

    @Autowired
    public MessageSenderServiceImpl(JmsTemplate jmsTemplate){
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void sendMessage(Object message) throws RuntimeException {
        this.jmsTemplate.convertAndSend(messageQueueName,message);
    }
}
