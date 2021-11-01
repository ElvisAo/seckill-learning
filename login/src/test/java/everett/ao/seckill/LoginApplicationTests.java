package everett.ao.seckill;

import everett.ao.seckill.utils.MD5Utils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LoginApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(MD5Utils.inputPass2submitPass("A1d9b961017#"));
    }

}
