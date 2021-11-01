package everett.ao.seckill.controller;

import everett.ao.seckill.constant.TokenConstant;
import everett.ao.seckill.entity.UserEntity;
import everett.ao.seckill.service.RedisService;
import everett.ao.seckill.service.UserService;
import everett.ao.seckill.utils.R;
import everett.ao.seckill.utils.UUIDUtils;
import everett.ao.seckill.utils.UserTokenPrefix;
import everett.ao.seckill.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    UserService userService;
    @Autowired
    RedisService redisService;

    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    @PostMapping("/do_login")   // post不能跳转页面
    @ResponseBody
    public R doLogin(HttpServletResponse response, @RequestBody @Valid LoginVo loginVo) {
//        if ((loginVo.getMobile()) == null) return R.error(CodeMsg.MOBILEEMPTY);
//        if (loginVo.getPassword() == null) return R.error(CodeMsg.PASSWORDEMPTY);
//        if (!ValidatorUtil.mobileValidate(loginVo.getMobile())) {
//            return R.error(CodeMsg.MOBILEPATTERNERROR);
//        }
        log.info("mobile=" + loginVo.getMobile() + " password=" + loginVo.getPassword());
        UserEntity user = userService.login(loginVo);
        // 用户登录后，生成一个token，然后把这个token写到cookie中传给客户端，
        String token = UUIDUtils.generateUUID();
        redisService.set(UserTokenPrefix.getByToken, token, user);
        Cookie cookie = new Cookie(TokenConstant.Cookie_Token_Key, token);
        cookie.setMaxAge(UserTokenPrefix.getByToken.expireSeconds());   // cookie和redis中的key的过期时间一致
        cookie.setPath(TokenConstant.Token_Path);
        response.addCookie(cookie);
        return R.ok(user);
//        if (user != null) {
//            if (user.getPassword().equals(MD5Utils.submitPass2dbPass(loginVo.getPassword(), user.getSalt())))
//                return R.ok(user);
//            else throw new GlobalException(CodeMsg.PASSWORDERROR);
//        } else throw new GlobalException(CodeMsg.MOBILTNOTEXIST);
    }
}
