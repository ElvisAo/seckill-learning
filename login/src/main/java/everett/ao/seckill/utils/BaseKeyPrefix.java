package everett.ao.seckill.utils;

public abstract class BaseKeyPrefix implements KeyPrefix {
    private Integer expireSeconds;
    private String prefix;

    // 0代表永不过期
    public BaseKeyPrefix(String prefix) {
        this(0, prefix);
    }

    public BaseKeyPrefix(Integer expireSeconds, String prefix){
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    @Override
    public Integer expireSeconds() {    // 默认0代表永不过期
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className + "." + prefix;
    }
}
