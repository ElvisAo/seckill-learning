package everett.ao.seckill.service;

import everett.ao.seckill.config.MQConfig;
import everett.ao.seckill.vo.SeckillMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MQSender {
    @Autowired
    AmqpTemplate amqpTemplate;


/*    public void send(Object msg) {
        String s = RedisService.serializeObject(msg);
        amqpTemplate.convertAndSend(MQConfig.QUEUE, s);
        log.info("send message:" + s);
    }

    public void sendTopic(Object msg) {
        String s = RedisService.serializeObject(msg);
        // 能匹配到两个
        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "key1", s + "1");
        // 只能匹配到queue2
        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "key2", s + "2");
        log.info("send message:" + s);
    }

    public void sendFanout(Object msg) {
        String s = RedisService.serializeObject(msg);
        // fanout需要第二个参数为空
        amqpTemplate.convertAndSend(MQConfig.FANOUT_EXCHANGE, "", s);
        log.info("fanout message:" + s);

    }

    public void senderHeaders(Object msg) {
        String s = RedisService.serializeObject(msg);
        MessageProperties properties = new MessageProperties();
        properties.setHeader("header1", "value1");
        properties.setHeader("header2", "value2");
        Message obj = new Message(s.getBytes(), properties);
        amqpTemplate.convertAndSend(MQConfig.HEADERS_EXHCANGE, "", obj);
        log.info("fanout message:" + s);
    }*/

    public void sendSeckillMessage(SeckillMessage seckillMessage) {
        String s = RedisService.serializeObject(seckillMessage);
        amqpTemplate.convertAndSend(MQConfig.SECKILL_QUEUE, s);
        log.info("send: " + s);
    }
}
