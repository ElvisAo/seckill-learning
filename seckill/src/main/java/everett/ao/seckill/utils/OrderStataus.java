package everett.ao.seckill.utils;

public enum OrderStataus {
    UNPAY(0, "未支付"),
    PAYED(1, "已支付");
    public int code;
    public String msg;

    private OrderStataus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
