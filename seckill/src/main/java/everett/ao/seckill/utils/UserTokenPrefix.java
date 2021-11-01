package everett.ao.seckill.utils;

import everett.ao.seckill.constant.TokenConstant;

public class UserTokenPrefix extends BaseKeyPrefix {
    public UserTokenPrefix(Integer expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public UserTokenPrefix(String prefix) {
        super(prefix);
    }

    public static UserTokenPrefix getByToken = new UserTokenPrefix(TokenConstant.Token_Expire_Time,"token.");
    public static UserTokenPrefix getById = new UserTokenPrefix(TokenConstant.Token_Expire_Time,"id.");
}
