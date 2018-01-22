package com.wecho.core.acmp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.*;


public class QueueReceiver{
    private JmsTemplate jmsTemplate;



    public void receiveMqMessage() {
        Destination destination = jmsTemplate.getDefaultDestination();
    }

    /**
     * 接受消息
     */

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @JmsListener(destination = "queueDestination")
    public void onMessage(Message message) {
        TextMessage tm = (TextMessage) message;
        try {
            System.out.println("从队列" + tm.getJMSDestination() + "收到了消息：\t" + tm.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}