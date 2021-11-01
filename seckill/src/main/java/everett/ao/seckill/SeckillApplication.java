package everett.ao.seckill;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

// 不继承这个initializer、重写configure方法，无法打成war包的情况下跑
@MapperScan(basePackages = {"everett.ao.seckill.mapper"})
@SpringBootApplication
public class SeckillApplication /*extends SpringBootServletInitializer */ {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(SeckillApplication.class, args);
    }

/*    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SeckillApplication.class);
    }*/
}
