package everett.ao.springbootstart.common.Utils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class BaseKeyPrefix implements KeyPrefix {
    private int expireSeconds;
    private String prefix;

    // 0代表永不过期
    public BaseKeyPrefix(String prefix) {
        this(0, prefix);
    }

    @Override
    public int expireSeconds() {    // 默认0代表永不过期
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className + "." + prefix;
    }
}
