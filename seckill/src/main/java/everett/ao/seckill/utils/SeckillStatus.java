package everett.ao.seckill.utils;


public enum SeckillStatus {
    UNSTART(0, "尚未开始"),
    STARTED(1, "正在进行"),
    ENDED(2, "已经结束");
    public int code;
    public String msg;

    private SeckillStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
