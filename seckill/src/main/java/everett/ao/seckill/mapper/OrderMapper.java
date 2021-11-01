package everett.ao.seckill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import everett.ao.seckill.entity.OrderInfoEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<OrderInfoEntity> {
}
