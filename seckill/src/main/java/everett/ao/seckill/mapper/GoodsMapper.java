package everett.ao.seckill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import everett.ao.seckill.entity.GoodsEntity;
import everett.ao.seckill.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodsMapper extends BaseMapper<GoodsEntity> {
    List<GoodsVo> listGoodsVo();
}
