package everett.ao.seckill.controller;

import everett.ao.seckill.config.TestConfigurationPropertiesConfig;
import everett.ao.seckill.entity.TestUserEntity;
import everett.ao.seckill.service.RedisService;
import everett.ao.seckill.service.TestUserService;
import everett.ao.seckill.utils.R;
import everett.ao.seckill.utils.UserTokenPrefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.JedisPool;

@Controller
@RequestMapping("/demo")
public class HelloController {
    @Autowired
    TestUserService userService;
    @Autowired
    TestConfigurationPropertiesConfig redisConfig;
    @Autowired
    RedisService redisService;
    @Autowired
    JedisPool redisPool;

    @GetMapping("/ok")
    @ResponseBody
    public String ok() {
        return "hello world";
    }

    @GetMapping("/error")
    @ResponseBody
    public R helloError() {
        return R.error();
    }

    @GetMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("name", "everett");
        return "hello";
    }

    @GetMapping("/db/{id}")
    @ResponseBody
    public R db(@PathVariable("id") Integer id) {
        return R.ok(userService.getById(id));
    }

    @GetMapping("/db/tx_test/{id}")
    @ResponseBody
    public R db_tx_test(@PathVariable("id") Integer id) {
        userService.deleteByidTx(id);
        return R.ok();
    }

    @GetMapping("/redis/configtest")
    @ResponseBody
    public R redisConfigTest() {
        return R.ok(redisConfig.getKey() + "=" + redisConfig.getVal());
    }

    @GetMapping("/redis/put/{key}")
    @ResponseBody
    public R redisPut(@PathVariable("key") String key) {
        TestUserEntity user = userService.getById(String.valueOf(key));
        Boolean res = redisService.set(UserTokenPrefix.getById, key, user);
        if (!res) return R.error();
        return R.ok(res);
    }

    @GetMapping("/redis/get/{key}")
    @ResponseBody
    public R redisGet(@PathVariable("key") String key) {
        return R.ok(redisService.get(UserTokenPrefix.getById, key, TestUserEntity.class));
    }

    @ExceptionHandler
    @ResponseBody
    public R handle(Exception e) {
        e.printStackTrace();
        return R.error();
    }
}
