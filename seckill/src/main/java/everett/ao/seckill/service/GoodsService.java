package everett.ao.seckill.service;

import everett.ao.seckill.vo.GoodsVo;

import java.util.List;

public interface GoodsService {
    List<GoodsVo> listGoodsVo();

    GoodsVo getGoodsVoByGoodsId(Integer id);

}
