package everett.ao.seckill.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import everett.ao.seckill.utils.JsonLongSerializer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("seckill_order")
public class SeckillOrderEntity {
    @TableId
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long id;
    private Integer userId;
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long orderId;
    private Integer goodsId;
}
