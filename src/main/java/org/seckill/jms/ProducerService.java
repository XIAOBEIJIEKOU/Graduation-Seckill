package org.seckill.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;

/**
 * @Author Zhuang YeFan
 * @Date
 * @Description
 **/
@Service
public class ProducerService {

    @Autowired
    JmsTemplate jmsTemplate;

    @Resource(name = "queueDestination")
    Destination queueDestination;

    @Resource(name = "topicDestination")
    Destination topicDestination;


    public void sendQueueMessage(final String message){
        jmsTemplate.send(queueDestination,new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage(message);
                System.out.println("消息队列发送消息："+ textMessage.getText());
                return textMessage;
            }
        });
    }

    public void sendTopicMessage(final String msg) {
        jmsTemplate.send(topicDestination,new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                System.out.println("向队列" + topicDestination.toString() + "发送了消息num------------" + msg);
                return session.createTextMessage(msg);
            }
        });
    }
}
