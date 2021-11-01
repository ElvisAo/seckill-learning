package everett.ao.seckill.utils;

import lombok.Getter;
import lombok.Setter;

/**
 * 为什么data不用模板？因为data不能是静态的，但是下面的ok和error等方法都是静态的
 * 如果不加getter、setter会406
 */
@Getter
@Setter
public class R {
    private int code;
    private String msg;
    private Object data;

    private static final R OK = new R(CodeMsg.SUCCESS.code, CodeMsg.SUCCESS.msg);
    private static final R ERROR = new R(CodeMsg.SERVERERROR.code, CodeMsg.SERVERERROR.msg);

    private R(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private R(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    // 不知道为什么，这里以及下面的error，不能返回R.OK或R.ERROR，否则前端访问406
    public static R ok() {
        return OK;
    }

    public static R ok(Object data) {
        return new R(CodeMsg.SUCCESS.code, CodeMsg.SUCCESS.msg, data);
    }

    public static R error() {
        return ERROR;
    }

    public static R error(CodeMsg cm) {
        return new R(cm.code, cm.msg, null);
    }
}
