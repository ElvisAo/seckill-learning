package everett.ao.seckill.utils;

public class GoodsKeyPrefix extends BaseKeyPrefix {
    public GoodsKeyPrefix(String prefix) {
        super(prefix);
    }

    public GoodsKeyPrefix(Integer expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static GoodsKeyPrefix getGoodsList = new GoodsKeyPrefix(60, "goods");
    public static GoodsKeyPrefix getGoodsById = new GoodsKeyPrefix(60, "id.");
    public static GoodsKeyPrefix getSekillGoodsStockCount = new GoodsKeyPrefix(0, "gs.");

}
