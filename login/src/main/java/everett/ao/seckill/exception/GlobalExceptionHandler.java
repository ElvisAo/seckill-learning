package everett.ao.seckill.exception;

import everett.ao.seckill.utils.CodeMsg;
import everett.ao.seckill.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    // 处理全局抛出的异常
    @ExceptionHandler({GlobalException.class})
    public R globalException(GlobalException e) {
        log.error(e.getMessage());
        return R.error(e.getCm());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public R bindException(HttpServletRequest request, MethodArgumentNotValidException e) throws Exception {
        log.error(e.getMessage());
        List<ObjectError> errors = e.getAllErrors();    // 参数校验有很多项错误
        return R.error(CodeMsg.ARGUMENTNOTVALIDERROR.fillArgs(errors));
    }

    @ExceptionHandler({Exception.class})
    public R exception(HttpServletRequest request, HttpServletResponse response, Exception e) {
        log.error(e.getMessage());
        return R.error(CodeMsg.SERVERERROR);
    }
}
