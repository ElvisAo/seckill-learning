package everett.ao.seckill.service;

import com.alibaba.fastjson.JSON;
import everett.ao.seckill.utils.KeyPrefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisService {
    @Autowired
    JedisPool redisPool;

    // 设置
    public <T> Boolean set(KeyPrefix prefix, String key, T value) {
        Jedis jedis = null;
        Boolean res = false;
        try {
            jedis = newJedis();
            String realKey = prefix.getPrefix() + key;
            int expireTime = prefix.expireSeconds();
            if (expireTime <= 0) {
                jedis.set(realKey, serializeObject(value));
            } else {
                jedis.setex(realKey, expireTime, serializeObject(value));
            }
            res = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeJedis(jedis);
            return res;
        }
    }

    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {
        Jedis jedis = null;
        T res = null;
        try {
            jedis = newJedis();
            String realKey = prefix.getPrefix() + key;
            String s = jedis.get(realKey);
            res = parseObject(s, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeJedis(jedis);
            return res;
        }
    }

    public Boolean exists(KeyPrefix prefix, String key) {
        String realKey = prefix.getPrefix();
        Jedis jedis = null;
        boolean res = false;
        try {
            jedis = newJedis();
            res = jedis.exists(realKey);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return res;
        }
    }

    /**
     * 把key对应的value值+1
     *
     * @param prefix
     * @param key
     * @return
     */
    public Long incr(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        Long res = -1L;
        try {
            jedis = newJedis();
            String realKey = prefix.getPrefix() + key;
            res = jedis.incr(realKey);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeJedis(jedis);
        }
        return res;
    }

    /**
     * 把key对应的value值-1，jedis的incr和decr是原子操作
     *
     * @param prefix
     * @param key
     * @return
     */
    public Long decr(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        Long res = -1L;
        try {
            jedis = newJedis();
            String realKey = prefix.getPrefix() + key;
            res = jedis.decr(realKey);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeJedis(jedis);
        }
        return res;
    }

    private Jedis newJedis() {
        return redisPool.getResource();
    }

    private void closeJedis(Jedis jedis) {
        if (jedis != null) jedis.close();
    }

    private <T> String serializeObject(T obj) {
        if (obj == null) return null;
        Class<?> clazz = obj.getClass();
        if (clazz == short.class || clazz == Short.class
                || clazz == char.class || clazz == Character.class
                || clazz == int.class || clazz == Integer.class
                || clazz == long.class || clazz == Long.class
                || clazz == float.class || clazz == Float.class
                || clazz == double.class || clazz == Double.class
        ) {
            return "" + obj;
        }
        return JSON.toJSONString(obj);
    }

    private <T> T parseObject(String s, Class<T> clazz) {
        if (s == null || s.length() == 0 || clazz == null) return null;
        return JSON.parseObject(s, clazz);
    }
}
