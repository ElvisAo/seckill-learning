package everett.ao.seckill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import everett.ao.seckill.entity.SeckillGoodsEntiity;
import everett.ao.seckill.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SeckillGoodsMapper extends BaseMapper<SeckillGoodsEntiity> {

    @Update("update seckill_goods set stock_count=stock_count-1 where goods_id=#{id} and stock_count>0")
    int updateStockCount(GoodsVo goodsVo);
}
