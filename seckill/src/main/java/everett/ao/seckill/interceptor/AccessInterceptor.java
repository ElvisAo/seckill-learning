package everett.ao.seckill.interceptor;

import com.alibaba.fastjson.JSON;
import com.mysql.cj.util.StringUtils;
import everett.ao.seckill.constant.TokenConstant;
import everett.ao.seckill.entity.UserEntity;
import everett.ao.seckill.service.RedisService;
import everett.ao.seckill.service.UserService;
import everett.ao.seckill.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AccessInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    UserService userService;
    @Autowired
    RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            UserEntity user = getLoginUser(request, response);
            UserContext.setUser(user);

            HandlerMethod hm = (HandlerMethod) handler;
            AccessLimit ac = hm.getMethodAnnotation(AccessLimit.class);
            if (ac == null) return true;

            int seconds = ac.seconds();
            int maxCount = ac.maxCount();
            boolean needLogin = ac.needLogin();
            String key = request.getRequestURI();
            if (needLogin) {
                if (user == null) {
                    render(response, CodeMsg.UNLOGINERROR);
                    return false;
                }
                key += "-" + user.getId();
            }
            AccessKeyPrefix prefix = AccessKeyPrefix.accessKEyWithExpire(seconds);
            Integer count = redisService.get(prefix, key, Integer.class);
            if (count == null || count.equals(1)) {
                redisService.set(prefix, key, 1);
                return true;
            } else if (count <= maxCount) {
                redisService.incr(prefix, key);
                return true;
            } else {
                render(response, CodeMsg.ACCESSLIMITERROR);
                return false;
            }
        }
        return true;
    }

    private void render(HttpServletResponse response, CodeMsg cm) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream os = response.getOutputStream();
        os.write(JSON.toJSONBytes(R.error(cm)));
        os.flush();
        os.close();
    }

    private UserEntity getLoginUser(HttpServletRequest request, HttpServletResponse response) {
        String paramToken = request.getParameter(TokenConstant.Cookie_Token_Key);
        String cookieToken = getCookieValueFromRequest(request);
        if (StringUtils.isNullOrEmpty(paramToken) && StringUtils.isNullOrEmpty(cookieToken)) return null;
        String token = StringUtils.isNullOrEmpty(paramToken) ? cookieToken : paramToken;
        return userService.getByToken(response, token);
    }

    private String getCookieValueFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) return null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(TokenConstant.Cookie_Token_Key)) return cookie.getValue();
        }
        return null;
    }
}
