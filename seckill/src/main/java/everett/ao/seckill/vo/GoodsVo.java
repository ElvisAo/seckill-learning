package everett.ao.seckill.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class GoodsVo{
    private Integer id;
    private String goodsName;
    private String goodsTitle;
    private String goodsImg;
    private String goodsDetail;
    private float goodsPrice;
    private Integer goodsStock;

    private float seckillPrice;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
}
