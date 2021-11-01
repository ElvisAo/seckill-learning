package everett.ao.seckill.controller;

import everett.ao.seckill.entity.UserEntity;
import everett.ao.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    UserService userService;

    // 为了兼容手机端，token可能会在param中带过来
    @RequestMapping("/to_list")
    public String toList(Model model, UserEntity userEntity) {
        model.addAttribute("user", userEntity);
        return "goods_list";
    }
}
