package everett.ao.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import everett.ao.seckill.entity.OrderInfoEntity;
import everett.ao.seckill.entity.SeckillOrderEntity;
import everett.ao.seckill.entity.UserEntity;
import everett.ao.seckill.mapper.OrderMapper;
import everett.ao.seckill.mapper.SeckillOrderMapper;
import everett.ao.seckill.service.OrderService;
import everett.ao.seckill.service.RedisService;
import everett.ao.seckill.utils.OrderKeyPrefix;
import everett.ao.seckill.utils.OrderStataus;
import everett.ao.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService {
    private static int counter = 0;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    SeckillOrderMapper seckillOrderMapper;
    @Autowired
    RedisService redisService;

    @Override
    public SeckillOrderEntity getSeckillOrderByUserIdGoodsId(int userId, Integer goodsId) {
        QueryWrapper<SeckillOrderEntity> wrapper = new QueryWrapper<SeckillOrderEntity>().eq("user_id", userId).eq("goods_id", goodsId);
        SeckillOrderEntity orderEntity = seckillOrderMapper.selectOne(wrapper);
        return orderEntity;
    }

    public <T> OrderInfoEntity getOrderInfoByUserIdGoodsIdFromRedis(Integer userId, Integer goodsId) {
        return redisService.get(OrderKeyPrefix.getOrderInfoByUidGid, userId + "" + goodsId, OrderInfoEntity.class);
    }

    @Override
    @Transactional
    public OrderInfoEntity createOrder(UserEntity user, GoodsVo goodsVo) {
        // 写order info
        OrderInfoEntity orderInfoEntity = new OrderInfoEntity.Builder()
                .setCreateDate(new Date())
                .setGoodsId(goodsVo.getId())
                .setGoodsName(goodsVo.getGoodsName())
                .setUserId(user.getId())
                .setGoodsPrice(goodsVo.getSeckillPrice())
                .setDeliveryAddrId(0)
                .setGoodsCount(1)
                .setOrderChannel(1)
                .setStatus(OrderStataus.UNPAY.code).build();
        orderMapper.insert(orderInfoEntity);
        // 写seckill order
        SeckillOrderEntity seckillOrder = new SeckillOrderEntity();
        seckillOrder.setOrderId(orderInfoEntity.getId());
        seckillOrder.setGoodsId(goodsVo.getId());
        seckillOrder.setUserId(user.getId());
        seckillOrderMapper.insert(seckillOrder);

        // 把订单id写到redis
        redisService.set(OrderKeyPrefix.getOrderInfoByUidGid, user.getId()+""+goodsVo.getId(),OrderInfoEntity.class);
//        System.out.println("                                  " + (++counter));
        return orderInfoEntity;
    }

    @Override
    public SeckillOrderEntity getSeckillOrderById(Long orderId) {
        return seckillOrderMapper.selectById(orderId);
    }

    @Override
    public OrderInfoEntity getOrderById(Long orderId) {
        return orderMapper.selectById(orderId);
    }
}
