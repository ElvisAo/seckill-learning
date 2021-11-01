package everett.ao.seckill.controller;

import everett.ao.seckill.entity.UserEntity;
import everett.ao.seckill.service.UserService;
import everett.ao.seckill.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Service
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/info/{id}")
    @ResponseBody
    public R info(@PathVariable("id") Integer id) {
        UserEntity user = userService.getById(id);
        return R.ok(user);
    }

    @RequestMapping("/info")
    @ResponseBody
    public R infoViaToken(UserEntity user){
        return R.ok(user);
    }
}
