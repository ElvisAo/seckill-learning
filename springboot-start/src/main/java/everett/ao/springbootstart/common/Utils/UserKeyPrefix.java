package everett.ao.springbootstart.common.Utils;

public class UserKeyPrefix extends BaseKeyPrefix {
    private UserKeyPrefix(String prefix) {
        super(prefix);
    }

    private UserKeyPrefix(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static UserKeyPrefix getById = new UserKeyPrefix("id.");
}
