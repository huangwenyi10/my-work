package com.example.mq.test;

import javax.jms.Destination;

public interface ConsumerService {

    public void receive(Destination queueDestination);

}
