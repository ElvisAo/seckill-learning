package everett.ao.seckill.controller;

import everett.ao.seckill.entity.SeckillOrderEntity;
import everett.ao.seckill.entity.UserEntity;
import everett.ao.seckill.service.*;
import everett.ao.seckill.utils.*;
import everett.ao.seckill.vo.GoodsVo;
import everett.ao.seckill.vo.SeckillMessage;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;


@Controller
@RequestMapping("/seckill")
public class SeckillController implements InitializingBean {
    @Autowired
    GoodsService goodsService;
    @Autowired
    OrderService orderService;
    @Autowired
    SeckillService seckillService;
    @Autowired
    RedisService redisService;
    @Autowired
    MQSender mqSender;

    private HashMap<Integer, Boolean> localOverMap = new HashMap<>();


    /**
     * 返回结果：
     * orderId：成功
     * -1：秒杀失败
     * 0：排队中
     *
     * @param goodsId
     * @param user
     * @return
     */
    @GetMapping("/result/{goodsId}")
    @ResponseBody
    public R queryResult(@PathVariable("goodsId") Integer goodsId, UserEntity user) {
        if (user == null) return R.error(CodeMsg.UNLOGINERROR);
        Long result = seckillService.getSeckillResult(user, goodsId);
        return R.ok(result);
    }

    @GetMapping("/get_seckillpath")
    @ResponseBody
    @AccessLimit(seconds = 5, maxCount = 5, needLogin = true)
    public R seckillPath(HttpServletRequest request, Model model, UserEntity user, @RequestParam("goodsId") Integer goodsId,
                         Integer verifyCode) {
        if (user == null) return R.error(CodeMsg.UNLOGINERROR);
        if (!seckillService.checkVerifyCode(user, goodsId, verifyCode)) {
            return R.error(CodeMsg.UNLEGALOPERATION);
        }
        String s = seckillService.createSeckillPath(user.getId(), goodsId);
        redisService.set(SeckillKeyPrefix.getSeckillPath, "" + user.getId() + "_" + goodsId, s);
        return R.ok(s);
    }

    @GetMapping("/verifyCode")
    @ResponseBody
    public R getSeckillVerifyCode(HttpServletResponse response, @RequestParam Integer goodsId, UserEntity user) {
        if (user == null) return R.error(CodeMsg.UNLOGINERROR);

        BufferedImage image = seckillService.createVerifyCode(user, goodsId);
        try {
            ServletOutputStream os = response.getOutputStream();
            ImageIO.write(image, "JPEG", os);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
            return R.error(CodeMsg.SERVERERROR);
        }
        return R.ok();
    }

    @PostMapping("/{path}/do_seckill")
    @ResponseBody
    public R doSeckill(Model model, UserEntity user, @PathVariable("path") String path,
                       @RequestParam("goodsId") Integer goodsId
    ) {
        if (user == null) return R.error(CodeMsg.UNLOGINERROR);
        if (!seckillService.checkPath(path, user.getId(), goodsId)) return R.error(CodeMsg.UNLEGALOPERATION);
        if (localOverMap.get(goodsId)) return R.error(CodeMsg.SECKILLOVER);
        // 预减库存：返回的是-1后的值
        Long stock = redisService.decr(GoodsKeyPrefix.getSekillGoodsStockCount, "" + goodsId);
        if (stock < 0) {
            localOverMap.put(goodsId, true);
            return R.error(CodeMsg.SECKILLOVER);
        }

        // 判断是否已经下单，查真正的数据库
        SeckillOrderEntity orderEntity = orderService.getSeckillOrderByUserIdGoodsId(user.getId(), goodsId);
        if (orderEntity != null) {
            redisService.incr(GoodsKeyPrefix.getSekillGoodsStockCount, "" + goodsId);
            return R.error(CodeMsg.REPEATESECKILL);
        }
        mqSender.sendSeckillMessage(new SeckillMessage(user, goodsId));

        return R.ok("排队中");
        /*GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        if (goodsVo.getStockCount() < 0) {
            model.addAttribute("errmsg", CodeMsg.SECKILLOVER.getMsg());
            return R.error(CodeMsg.SECKILLOVER);
        }
        // 判断是否已经下单
        SeckillOrderEntity orderEntity = orderService.getSeckillOrderByUserIdGoodsId(user.getId(), goodsId);
        if (orderEntity != null) {
            model.addAttribute("errmsg", CodeMsg.REPEATESECKILL.getMsg());
            return R.error(CodeMsg.REPEATESECKILL);
        }
        // 减库存 下订单 写入秒杀订单:需要在一个事务里面
        OrderInfoEntity orderInfoEntity = seckillService.seckill(user, goodsVo);
        model.addAttribute("orderInfo", orderInfoEntity);
        model.addAttribute("goodsId", goodsId);
        model.addAttribute("goods", goodsVo);
        System.out.println(orderInfoEntity.getId());
        return R.ok(orderInfoEntity);*/
    }

    // 系统初始化时做一些事情
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsVos = goodsService.listGoodsVo();
        if (goodsVos == null) return;
        for (GoodsVo g : goodsVos) {
            redisService.set(GoodsKeyPrefix.getSekillGoodsStockCount, "" + g.getId(), g.getStockCount());
            localOverMap.put(g.getId(), false);
        }
    }
}
