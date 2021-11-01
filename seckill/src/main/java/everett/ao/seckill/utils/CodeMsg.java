package everett.ao.seckill.utils;

import lombok.Getter;

@Getter
public enum CodeMsg {
    SUCCESS(0, "success"),

    // 通用异常：500100开始
    SERVERERROR(500100, "服务器异常"),
    ARGUMENTNOTVALIDERROR(500101, "参数校验异常:%s"),
    ACCESSLIMITERROR(500102,"访问太频繁"),
    // 登录模块：500200开始
    SESSIONERROR(500210, "Session不存在或者已失效"),
    PASSWORDEMPTY(500211, "密码不能为空"),
    PASSWORDERROR(500215, "密码错误"),
    USERNOTEXIST(500126, "用户不存在"),
    UNLOGINERROR(500126, "请先登录"),

    MOBILEEMPTY(500212, "手机号不能为空"),
    MOBILEPATTERNERROR(500213, "手机号码格式错误"),
    MOBILTNOTEXIST(500214, "手机号不存在"),
    // 商品模块：500300开始

    // 订单模块：500400开始
    ORDERNOTEXIST(500400, "订单不存在"),
    // 秒杀模块：500500开始
    SECKILLOVER(500500, "商品已经秒杀完毕"),
    UNLEGALOPERATION(500501,"非法操作"),

    REPEATESECKILL(500501, "不能重复秒杀");

    int code;
    String msg;

    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    // 只能是ARGUMENTNOTVALIDERROR调用
    public CodeMsg fillArgs(Object... args) throws Exception {
        if (this.code != ARGUMENTNOTVALIDERROR.code) throw new Exception("Operation not supported");
        this.msg = String.format(this.msg, args);
        return this;
    }
}
