package everett.ao.seckill.controller;

import everett.ao.seckill.entity.OrderInfoEntity;
import everett.ao.seckill.entity.UserEntity;
import everett.ao.seckill.service.OrderService;
import everett.ao.seckill.utils.CodeMsg;
import everett.ao.seckill.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping("/order_detail")
    @ResponseBody
    public R orderDetail(Model model, UserEntity user, @RequestParam("orderId") Long orderId) {
        if(user == null)return R.error(CodeMsg.UNLOGINERROR);
        OrderInfoEntity orderInfoEntity = orderService.getOrderById(orderId);
        if(orderInfoEntity == null) return R.error(CodeMsg.ORDERNOTEXIST);
        return R.ok(orderInfoEntity);
    }
}
