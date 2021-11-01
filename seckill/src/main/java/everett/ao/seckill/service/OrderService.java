package everett.ao.seckill.service;

import everett.ao.seckill.entity.OrderInfoEntity;
import everett.ao.seckill.entity.SeckillOrderEntity;
import everett.ao.seckill.entity.UserEntity;
import everett.ao.seckill.vo.GoodsVo;

public interface OrderService {
    SeckillOrderEntity getSeckillOrderByUserIdGoodsId(int userId, Integer goodsId);

    OrderInfoEntity createOrder(UserEntity user, GoodsVo goodsVo);

    SeckillOrderEntity getSeckillOrderById(Long orderId);

    OrderInfoEntity getOrderById(Long orderId);
}
