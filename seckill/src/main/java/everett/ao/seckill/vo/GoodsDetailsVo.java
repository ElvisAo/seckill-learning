package everett.ao.seckill.vo;

import everett.ao.seckill.entity.UserEntity;
import everett.ao.seckill.utils.SeckillStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GoodsDetailsVo {
    GoodsVo goodsVo;
    Integer startRemains;
    Integer endRemains;
    SeckillStatus seckillStatus;
    UserEntity user;
}
