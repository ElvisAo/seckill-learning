package everett.ao.seckill.service;

import everett.ao.seckill.config.MQConfig;
import everett.ao.seckill.entity.SeckillOrderEntity;
import everett.ao.seckill.entity.UserEntity;
import everett.ao.seckill.vo.GoodsVo;
import everett.ao.seckill.vo.SeckillMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MQReceiver {

    @Autowired
    GoodsService goodsService;
    @Autowired
    OrderService orderService;
    @Autowired
    SeckillService seckillService;

    @RabbitListener(queues = {MQConfig.SECKILL_QUEUE})
    public void receiveSeckillMessage(String msg) {
        log.info("receive message:" + msg);
        SeckillMessage seckillMessage = RedisService.parseObject(msg, SeckillMessage.class);
        UserEntity user = seckillMessage.getUser();
        Integer goodsId = seckillMessage.getGoodsId();

        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goodsVo.getStockCount();
        if (stock < 0) return;

        // 判断是否已经下单，查真正的数据库
        SeckillOrderEntity orderEntity = orderService.getSeckillOrderByUserIdGoodsId(user.getId(), goodsId);
        if (orderEntity != null) return;

        seckillService.seckill(user, goodsVo);   // 这里没有直接返回给客户秒杀的结果，而是由客户端轮询来查订单

    }

/*    @RabbitListener(queues = {MQConfig.TOPIC_QUEUE1})
    public void receiveTopic1(String msg) {
        log.info("receive topic.queue1 message:" + msg);
    }

    @RabbitListener(queues = {MQConfig.TOPIC_QUEUE2})
    public void receiveTopic2(String msg) {
        log.info("receive topic.queue2 message:" + msg);
    }

    @RabbitListener(queues = {MQConfig.HEADERS_QUEUE})
    public void receiveHeaders(byte[] msg) {
        log.info("receive headers msg:" + new String(msg));
    }*/
}
