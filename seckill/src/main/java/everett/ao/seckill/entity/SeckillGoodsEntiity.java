package everett.ao.seckill.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@TableName("seckill_goods")
public class SeckillGoodsEntiity {
    @TableId
    private Integer id;
    private Integer goodsId;
    private Float seckillPrice;
    private int stockCount;
    private Date startDate;
    private Date endDate;
}
