package everett.ao.seckill.utils;

public class AccessKeyPrefix extends BaseKeyPrefix{
    public AccessKeyPrefix(String prefix) {
        super(prefix);
    }

    public AccessKeyPrefix(Integer expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static final AccessKeyPrefix access = new AccessKeyPrefix(5,"access.");
    public static AccessKeyPrefix accessKEyWithExpire(int expirySeconds){
        return new AccessKeyPrefix(expirySeconds, "access.");
    }
}
