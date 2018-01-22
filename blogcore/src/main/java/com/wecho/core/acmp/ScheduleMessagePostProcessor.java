package com.wecho.core.acmp;

import org.apache.activemq.ScheduledMessage;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.util.StringUtils;

import javax.jms.JMSException;
import javax.jms.Message;

public class ScheduleMessagePostProcessor implements MessagePostProcessor {

    private long delay = 0l;

    private String corn = null;

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public String getCorn() {
        return corn;
    }

    public void setCorn(String corn) {
        this.corn = corn;
    }

    public ScheduleMessagePostProcessor(long delay) {
        this.delay = delay;
    }

    public ScheduleMessagePostProcessor(String cron) {
        this.corn = cron;
    }

    @Override
    public Message postProcessMessage(Message message) throws JMSException {
        if (delay > 0) {
            message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delay);
        }
        if (!StringUtils.isEmpty(corn)) {
            message.setStringProperty(ScheduledMessage.AMQ_SCHEDULED_CRON, corn);
        }
        return message;
    }
}