package everett.ao.seckill.controller;

import com.mysql.cj.util.StringUtils;
import everett.ao.seckill.entity.UserEntity;
import everett.ao.seckill.service.GoodsService;
import everett.ao.seckill.service.RedisService;
import everett.ao.seckill.service.UserService;
import everett.ao.seckill.utils.GoodsKeyPrefix;
import everett.ao.seckill.utils.R;
import everett.ao.seckill.utils.SeckillStatus;
import everett.ao.seckill.vo.GoodsDetailsVo;
import everett.ao.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    UserService userService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    RedisService redisService;
    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;


    // 为了兼容手机端，token可能会在param中带过来
    @RequestMapping(value = "/to_list", produces = {"text/html"})
    @ResponseBody
    public String toList(Model model, UserEntity userEntity, HttpServletRequest request, HttpServletResponse response) {
//        model.addAttribute("user", userEntity);
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);
//        return "goods_list";
        String html = redisService.get(GoodsKeyPrefix.getGoodsList, "", String.class);
        if (!StringUtils.isNullOrEmpty(html)) {
            return html;
        }
        IWebContext ctx = new WebContext(request, response,
                request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);
        if (!StringUtils.isNullOrEmpty(html)) redisService.set(GoodsKeyPrefix.getGoodsList, "", html);
        return html;
    }

    // 一般互联网开发中，为了避免被人遍历库，id一般不使用自增的，而是使用snowflake算法
    @GetMapping(value = "/to_detail2/{id}", produces = {"text/html"})
    @ResponseBody
    public String toDetail2(@PathVariable("id") Integer id, Model model, UserEntity user, HttpServletResponse response, HttpServletRequest request) {
        model.addAttribute("user", user);
        String html = redisService.get(GoodsKeyPrefix.getGoodsById, "" + id, String.class);
        if (!StringUtils.isNullOrEmpty(html)) return html;
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(id);
        model.addAttribute("goods", goodsVo);
        long start = goodsVo.getStartDate().getTime();
        long now = System.currentTimeMillis();
        long end = goodsVo.getEndDate().getTime();
        long startRemains = 0;
        long endRemains = 0;
        SeckillStatus seckillStatus = null;
        if (now < start) {
            // 倒计时
            startRemains = (start - now) / 1000;
            endRemains = (end - now) / 1000;
            seckillStatus = SeckillStatus.UNSTART;
        } else if (now > end) {
            //  已经结束
            seckillStatus = SeckillStatus.ENDED;
        } else {
            seckillStatus = SeckillStatus.STARTED;
            endRemains = (end - now) / 1000;
        }
        model.addAttribute("seckillStatus", seckillStatus.code);
        model.addAttribute("startRemains", startRemains);
        model.addAttribute("endRemains", endRemains);
        IWebContext ctx = new WebContext(request, response,
                request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", ctx);
        if (!StringUtils.isNullOrEmpty(html)) redisService.set(GoodsKeyPrefix.getGoodsList, "", html);
        return html;
    }

    @GetMapping(value = "/to_detail/{id}")
    @ResponseBody
    public R toDetail(@PathVariable("id") Integer id, Model model, UserEntity user, HttpServletResponse response, HttpServletRequest request) {

        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(id);
        model.addAttribute("goods", goodsVo);
        long start = goodsVo.getStartDate().getTime();
        long now = System.currentTimeMillis();
        long end = goodsVo.getEndDate().getTime();
        int startRemains = 0;
        int endRemains = 0;
        SeckillStatus seckillStatus = null;
        if (now < start) {
            // 倒计时
            startRemains = (int) ((start - now) / 1000);
            endRemains = (int) ((end - now) / 1000);
            seckillStatus = SeckillStatus.UNSTART;
        } else if (now > end) {
            //  已经结束
            seckillStatus = SeckillStatus.ENDED;
        } else {
            seckillStatus = SeckillStatus.STARTED;
            endRemains = (int) ((end - now) / 1000);
        }
        GoodsDetailsVo goodsDetailsVo = new GoodsDetailsVo(goodsVo, startRemains, endRemains, seckillStatus, user);
        return R.ok(goodsDetailsVo);
    }
}
