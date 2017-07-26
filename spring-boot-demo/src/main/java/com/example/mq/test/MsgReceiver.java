package com.example.mq.test;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * 描述：消息接收者
 * @author Ay
 * @version 1.0.0
 */
@Component
public class MsgReceiver {

    @JmsListener(destination = "myQueue")
    public void receiveMessage(String message) {
        System.out.println("接收到消息：" + message);
    }

}

