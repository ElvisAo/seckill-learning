package everett.ao.seckill.config;

import com.mysql.cj.util.StringUtils;
import everett.ao.seckill.constant.TokenConstant;
import everett.ao.seckill.entity.UserEntity;
import everett.ao.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> clazz = parameter.getParameterType();
        if (clazz == UserEntity.class) return true; // 如果需要的参数是UserEntity，才进行处理，否则不处理
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter
            , ModelAndViewContainer mavContainer
            , NativeWebRequest webRequest
            , WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        String paramToken = request.getParameter(TokenConstant.Cookie_Token_Key);
         String cookieToken = getCookieValueFromRequest(request);
        if (StringUtils.isNullOrEmpty(paramToken) && StringUtils.isNullOrEmpty(cookieToken)) return null;
        String token = StringUtils.isNullOrEmpty(paramToken) ? cookieToken : paramToken;
        return userService.getByToken(response, token);
    }

    private String getCookieValueFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(TokenConstant.Cookie_Token_Key)) return cookie.getValue();
        }
        return null;
    }
}
