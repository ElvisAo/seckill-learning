package everett.ao.springbootstart.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "redis")  // 必须要有setter方法
public class RedisConfig {
    private String host;
    private int port;
    private int poolMaxTotal;
    private int poolMaxIdle;
    private int poolMaxWaitMillis;
    private int connectionTimeout;

    @Bean
    public JedisPool createJedisPool() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(poolMaxTotal);
        poolConfig.setMaxIdle(poolMaxIdle);
        poolConfig.setMaxWaitMillis(poolMaxWaitMillis);
        JedisPool pool = new JedisPool(poolConfig, host, port, connectionTimeout);
        return pool;
    }
}
