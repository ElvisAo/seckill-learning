package everett.ao.springbootstart.common.Utils;

public class OrderKeyPrefix extends BaseKeyPrefix {
    public OrderKeyPrefix(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public OrderKeyPrefix(String prefix) {
        super(prefix);
    }

    public static OrderKeyPrefix getById = new OrderKeyPrefix("id.");
}
