package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author Zhuang YeFan
 * @Date
 * @Description
 **/

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/activemqProducer.xml"})
public class ActiveMQProducerDaoServiceTest {

//    @Autowired
//    private ActiveMQProducerService activeMQProducerService;

    @Test
    public void sendQueueMessage() {

        String msg = "This is a message from spring";
//        new ActiveMQProducerService().sendQueueMessage(msg);
    }

    @Test
    public void sendMessage() {
    }
}