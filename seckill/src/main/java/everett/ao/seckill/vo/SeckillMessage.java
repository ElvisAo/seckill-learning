package everett.ao.seckill.vo;

import everett.ao.seckill.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SeckillMessage {
    private UserEntity user;
    private Integer goodsId;
}
