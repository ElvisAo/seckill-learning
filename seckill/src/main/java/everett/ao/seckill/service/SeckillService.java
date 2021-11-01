package everett.ao.seckill.service;


import everett.ao.seckill.entity.OrderInfoEntity;
import everett.ao.seckill.entity.UserEntity;
import everett.ao.seckill.vo.GoodsVo;

import java.awt.image.BufferedImage;

public interface SeckillService {
    OrderInfoEntity seckill(UserEntity user, GoodsVo goodsVo);

    Long getSeckillResult(UserEntity user, Integer goodsId);

    Boolean checkPath(String path, Integer userId, Integer goodsId);

    String createSeckillPath(Integer userId, Integer goodsId);

    BufferedImage createVerifyCode(UserEntity user, Integer goodsId);

    boolean checkVerifyCode(UserEntity user, Integer goodsId, Integer verifyCode);
}
