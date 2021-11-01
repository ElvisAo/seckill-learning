package everett.ao.seckill.utils;

public class SeckillKeyPrefix extends BaseKeyPrefix {
    public SeckillKeyPrefix(String prefix) {
        super(prefix);
    }

    public SeckillKeyPrefix(Integer expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static SeckillKeyPrefix isGoodsOver = new SeckillKeyPrefix(0, "goodsOver.");
    public static SeckillKeyPrefix getSeckillPath = new SeckillKeyPrefix(60, "seckillPath.");
    public static SeckillKeyPrefix getVerifyCode = new SeckillKeyPrefix(60 * 5, "verifyCode.");

}
