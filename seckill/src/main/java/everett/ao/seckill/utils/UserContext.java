package everett.ao.seckill.utils;

import everett.ao.seckill.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserContext {
    private static ThreadLocal<UserEntity> userHolder = new ThreadLocal<>();

    public static void setUser(UserEntity user) {
        userHolder.set(user);
    }

    public static UserEntity getUser() {
        return userHolder.get();
    }
}
