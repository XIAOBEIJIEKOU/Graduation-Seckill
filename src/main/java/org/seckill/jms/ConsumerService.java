package org.seckill.jms;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.entity.SuccessKilled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.IOException;

/**
 * @Author Zhuang YeFan
 * @Date
 * @Description
 **/
@Service
public class ConsumerService implements MessageListener{

    @Autowired
    private SuccessKilledDao successKilledDao;

    public void onMessage(Message messageJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            SuccessKilled successKilled = objectMapper.readValue(((TextMessage)messageJson).getText(), SuccessKilled.class);
            successKilledDao.insertSuccessKilled(successKilled.getSeckillId(),successKilled.getUserPhone());
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}