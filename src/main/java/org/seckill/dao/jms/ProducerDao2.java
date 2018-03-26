//package org.seckill.dao.jms;
//
//import org.apache.activemq.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.jms.*;
//
///**
// * @Author Zhuang YeFan
// * @Date
// * @Description
//// **/
//public class ProducerDao2 {
//    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerDao2.class);
//    private static final String BROKER_URL = ActiveMQConnection.DEFAULT_BROKER_URL;
//    private static final String SUBJECT = "test-activemq-queue";
//
//    public void sendMsg() throws JMSException{
//        //初始化连接工厂
//        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
//        //获得连接
//        Connection conn = connectionFactory.createConnection();
//        //启动连接
//        conn.start();
//
//        //创建Session，此方法第一个参数表示会话是否在事务中执行，第二个参数设定会话的应答模式
//        Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
//
//        //创建队列
//        Destination dest = session.createQueue(SUBJECT);
//        //createTopic方法用来创建Topic
//        //session.createTopic("TOPIC");
//
//        //通过session可以创建消息的生产者
//        MessageProducer producer = session.createProducer(dest);
//        for (int i=0;i<100;i++) {
//            //初始化一个mq消息
//            TextMessage message = session.createTextMessage("hello active mq 中文" + i);
//            //发送消息
//            producer.send(message);
//            LOGGER.debug("send message {}", i);
//        }
//
//        //关闭mq连接
//        conn.close();
//    }
//
//    public static void main(String[] args) throws JMSException {
//
//
//    }
//}
