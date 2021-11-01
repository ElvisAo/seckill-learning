package everett.ao.seckill.utils;

public class UserKeyPrefix extends BaseKeyPrefix {
    private UserKeyPrefix(String prefix) {
        super(prefix);
    }

    private UserKeyPrefix(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static UserKeyPrefix getById = new UserKeyPrefix("id.");
}
