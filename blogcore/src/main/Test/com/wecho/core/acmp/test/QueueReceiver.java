package com.wecho.core.acmp.test;

import org.junit.Test;

import javax.jms.*;

import org.junit.runner.RunWith;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.jms.Message;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-mq-queue.xml"})
public class QueueReceiver implements MessageListener {
    @Resource
    private JmsTemplate jmsTemplate;
    @Test
    public void receiveMqMessage(){
        Destination destination = jmsTemplate.getDefaultDestination();
        receive(destination);
    }
    /**
     * 接受消息
     */
    public void receive(Destination destination) {

    }
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void onMessage(Message message) {
        TextMessage tm = (TextMessage) message;
        try {
            System.out.println("从队列" + tm.getJMSDestination() + "收到了消息：\t" + tm.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}