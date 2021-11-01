package everett.ao.springbootstart.common.Utils;

public enum CodeMsg{
    SUCCESS(0, "success"),

    // 通用异常：500100开始
    SERVERERROR(500100, "服务器异常");
    // 登录模块：500200开始

    // 商品模块：500300开始

    // 订单模块：500400开始

    // 秒杀模块：500500开始


    int code;
    String msg;

    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
