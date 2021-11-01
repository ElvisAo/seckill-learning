package everett.ao.seckill.service.impl;

import everett.ao.seckill.entity.OrderInfoEntity;
import everett.ao.seckill.entity.SeckillOrderEntity;
import everett.ao.seckill.entity.UserEntity;
import everett.ao.seckill.exception.GlobalException;
import everett.ao.seckill.mapper.GoodsMapper;
import everett.ao.seckill.mapper.SeckillGoodsMapper;
import everett.ao.seckill.service.OrderService;
import everett.ao.seckill.service.RedisService;
import everett.ao.seckill.service.SeckillService;
import everett.ao.seckill.utils.CodeMsg;
import everett.ao.seckill.utils.MD5Utils;
import everett.ao.seckill.utils.SeckillKeyPrefix;
import everett.ao.seckill.utils.UUIDUtils;
import everett.ao.seckill.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

@Slf4j
@Service
public class SeckillServiceImpl implements SeckillService {
    //    private static int counter = 0;
//    private static HashMap<Integer, Integer> map = new HashMap<>();
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    SeckillGoodsMapper seckillGoodsMapper;
    @Autowired
    OrderService orderService;
    @Autowired
    RedisService redisService;


    @Override
    @Transactional
    public OrderInfoEntity seckill(UserEntity user, GoodsVo goodsVo) {
        // 减库存 下订单 写入秒杀
        if (this.reduceStock(goodsVo)) {
            // order_info seckill_order
            OrderInfoEntity orderInfoEntity = orderService.createOrder(user, goodsVo);
            return orderInfoEntity;
        }
        // 如果减库存失败，就做个标记，表示商品已经被秒杀完了
        setGoodsOver(goodsVo.getId());
        throw new GlobalException(CodeMsg.SECKILLOVER);
    }

    private void setGoodsOver(Integer id) {
        redisService.set(SeckillKeyPrefix.isGoodsOver, "" + id, true);
    }

    private Boolean getGoodsOver(Integer id) {
        return redisService.exists(SeckillKeyPrefix.isGoodsOver, "" + id);
    }

    @Override
    public Long getSeckillResult(UserEntity user, Integer goodsId) {
        SeckillOrderEntity seckillOrderEntity = orderService.getSeckillOrderByUserIdGoodsId(user.getId(), goodsId);
        if (seckillOrderEntity != null) return seckillOrderEntity.getOrderId();
        else {
            Boolean isOver = getGoodsOver(goodsId);
            if (isOver) return -1L;
            else return 0L;
        }
    }

    @Override
    public Boolean checkPath(String path, Integer userId, Integer goodsId) {
        if (path == null) return false;
        String existedPath = redisService.get(SeckillKeyPrefix.getSeckillPath, "" + userId + "_" + goodsId, String.class);
        if (existedPath == null) return false;
        return existedPath.equals(path);
    }

    @Override
    public String createSeckillPath(Integer userId, Integer goodsId) {
        return MD5Utils.inputPass2dbPass(UUIDUtils.generateUUID() + userId + goodsId);
    }

    @Override
    public BufferedImage createVerifyCode(UserEntity user, Integer goodsId) {
        if (user == null || goodsId == null) return null;
        int width = 80, height = 32;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setColor(new Color(0xDCDCDC));
        g.fillRect(0, 0, width, height);
        g.setColor(Color.black);
        g.drawRect(0, 0, width - 1, height - 1);
        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            g.drawOval(x, y, 0, 0);
        }
        String verifyCode = generateVerifyCode(random);
        g.setColor(new Color(0, 100, 0));
        g.setFont(new Font("Canadara", Font.BOLD, 24));
        g.drawString(verifyCode, 8, 24);
        g.dispose();
        // 验证码存到redis中
        int rnd = calc(verifyCode);
        redisService.set(SeckillKeyPrefix.getVerifyCode, "" + user.getId() + "-" + goodsId, rnd);

        return image;
    }

    @Override
    public boolean checkVerifyCode(UserEntity user, Integer goodsId, Integer verifyCode) {
        if (user == null || goodsId == null || verifyCode == null) return false;
        Integer oldCode = redisService.get(SeckillKeyPrefix.getVerifyCode, "" + user.getId() + "-" + goodsId, Integer.class);
        if(!verifyCode.equals(oldCode)) return false;
        redisService.delete(SeckillKeyPrefix.getVerifyCode, "" + user.getId() + "-" + goodsId);
        return true;
    }

    /*
        public static void main(String[] args) {
            System.out.println(calc("1+2*3"));
        }*/
    private int calc(String verifyCode) {
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            return (Integer) engine.eval(verifyCode);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static char[] ops = new char[]{'+', '-', '*'};

    /**
     * 只做+-*
     *
     * @param random
     * @return
     */
    private String generateVerifyCode(Random random) {
        int number1 = random.nextInt(10);
        int number2 = random.nextInt(10);
        int number3 = random.nextInt(10);
        int op1 = random.nextInt(3);
        int op2 = random.nextInt(3);
        String exp = "" + number1 + ops[op1] + number2 + ops[op2] + number3;
        return exp;
    }

    public boolean reduceStock(GoodsVo goodsVo) {
        /*UpdateWrapper<SeckillGoodsEntiity> updateWrapper = new UpdateWrapper<SeckillGoodsEntiity>()
                .eq("goods_id", goodsVo.getId()).gt("stock_count", 0)
                .set("stock_count", goodsVo.getStockCount() - 1);
//        changeCount(goodsVo.getStockCount());
        return seckillGoodsMapper.update(null, updateWrapper) > 0;*/
        return seckillGoodsMapper.updateStockCount(goodsVo) > 0;
//        System.out.println(++counter);
    }

//    private void changeCount(int stockCount) {
//        map.put(stockCount, map.getOrDefault(stockCount, 0) + 1);
//    }
}
