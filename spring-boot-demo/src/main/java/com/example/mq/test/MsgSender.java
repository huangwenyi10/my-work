package com.example.mq.test;

import org.springframework.jms.core.MessageCreator;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * 消息发送者
 * @author ay
 * @version 1.0.0
 */
public class MsgSender implements MessageCreator {
    @Override
    public Message createMessage(Session session) throws JMSException {
        return session.createTextMessage("测试消息");
    }
}
