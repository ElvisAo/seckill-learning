package everett.ao.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import everett.ao.seckill.entity.GoodsEntity;
import everett.ao.seckill.entity.SeckillGoodsEntiity;
import everett.ao.seckill.mapper.GoodsMapper;
import everett.ao.seckill.mapper.SeckillGoodsMapper;
import everett.ao.seckill.service.GoodsService;
import everett.ao.seckill.vo.GoodsVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    SeckillGoodsMapper seckillGoodsMapper;

    @Override
    public List<GoodsVo> listGoodsVo() {
        return goodsMapper.listGoodsVo();
    }

    @Override
    public GoodsVo getGoodsVoByGoodsId(Integer id) {
        GoodsEntity goodsEntity = goodsMapper.selectById(id);

        QueryWrapper<SeckillGoodsEntiity> seckillGoodsEntiityQueryWrapper = new QueryWrapper<SeckillGoodsEntiity>().eq("goods_id", id);
        SeckillGoodsEntiity seckillGoodsEntiity = seckillGoodsMapper.selectOne(seckillGoodsEntiityQueryWrapper);

        GoodsVo goodsVo = new GoodsVo();
        BeanUtils.copyProperties(goodsEntity, goodsVo);
        goodsVo.setSeckillPrice(seckillGoodsEntiity.getSeckillPrice());
        goodsVo.setStockCount(seckillGoodsEntiity.getStockCount());
        goodsVo.setStartDate(seckillGoodsEntiity.getStartDate());
        goodsVo.setEndDate(seckillGoodsEntiity.getEndDate());
        return goodsVo;
    }
}
