package everett.ao.seckill.controller;

import everett.ao.seckill.service.MQReceiver;
import everett.ao.seckill.service.MQSender;
import everett.ao.seckill.service.RedisService;
import everett.ao.seckill.service.UserService;
import everett.ao.seckill.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class SampleController {
    @Autowired
    UserService userService;
    @Autowired
    RedisService redisService;
    @Autowired
    MQSender mqSender;
    @Autowired
    MQReceiver mqReceiver;
    AtomicInteger counter = new AtomicInteger(0);

    @RequestMapping("/mq/send")
    @ResponseBody
    public R mq() {
//        mqSender.sendFanout("hello,rabbitmq");
        return R.ok();
    }

    @ResponseBody
    @RequestMapping("/mq/sendheader")
    public R mqheader() {
//        mqSender.senderHeaders("hello,rabbitmq");
        return R.ok();
    }

}
