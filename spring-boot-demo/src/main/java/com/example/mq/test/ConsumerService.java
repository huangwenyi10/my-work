package com.example.mq.test;

import org.springframework.stereotype.Component;

import javax.jms.Destination;

public interface ConsumerService {
    public void receive(Destination queueDestination);
}
