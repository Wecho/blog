package com.wecho.core.acmp.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import javax.annotation.Resource;
import javax.jms.*;

import javax.annotation.Resource;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-mq-queue.xml"})
public class QueueSender {
    @Resource
    private JmsTemplate jmsTemplate;
    @Test
    public void send(){
        sendMqMessage(null,"spring activemq queue type message !");
    }
    /**
     * 说明:发送的时候如果这里没有显示的指定destination.将用spring xml中配置的destination
     * @param destination
     * @param message
     */
    public void sendMqMessage(Destination destination, final String message){
        if(null == destination){
            destination = jmsTemplate.getDefaultDestination();
        }
       /* jmsTemplate.send(destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        });*/
        Object message2 = "message：" + new Date();
        jmsTemplate.convertAndSend(message2, new ScheduleMessagePostProcessor(30 * 1000));//延时30秒
        System.out.println("spring send message...");
    }
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }
}