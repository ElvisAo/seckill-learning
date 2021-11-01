package everett.ao.seckill;

import everett.ao.seckill.entity.UserEntity;
import everett.ao.seckill.service.RedisService;
import everett.ao.seckill.service.UserService;
import everett.ao.seckill.utils.MD5Utils;
import everett.ao.seckill.utils.UUIDUtils;
import everett.ao.seckill.utils.UserTokenPrefix;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.List;
import java.util.Random;

@SpringBootTest
class SeckillApplicationTests {

    @Autowired
    UserService userService;
    @Autowired
    RedisService redisService;

    @Test
    void contextLoads() {
        System.out.println(MD5Utils.inputPass2submitPass("A1d9b961017#"));
    }

    @Test
    void login() {
        File file = new File("E:\\Everett\\OneDrive\\桌面\\test_id_token.csv");
        List<UserEntity> users = userService.queryAllUser();
    }

    @Test
    void createTestData() throws IOException {
        Random random = new Random();
        File fout = new File("E:\\Everett\\OneDrive\\桌面\\test_id_token.csv");
        FileOutputStream fos = new FileOutputStream(fout);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        for (int it = 0; it < 50000; it++) {
            String userName = UUIDUtils.generateUUID().substring(0, 5);
            String inputPass = UUIDUtils.generateUUID().substring(0, 8);
            String mobile = "" + 1;
            for (int i = 0; i < 10; i++) {
                mobile += random.nextInt(10);
            }
            String finalPass = MD5Utils.inputPass2dbPass(inputPass);
            UserEntity userEntity = new UserEntity();
            userEntity.setMobile(mobile);
            userEntity.setNickname(userName);
            userEntity.setPassword(finalPass);
            userEntity.setSalt("1a2b3c4d");

            UserEntity registered = userService.register(userEntity);

            Integer id = registered.getId();
            String token = UUIDUtils.generateUUID();

            redisService.set(UserTokenPrefix.getByToken, token, registered);

            bw.write(id + "," + token);
            bw.newLine();
        }
        bw.close();
        fos.close();
    }

    @Test
    void registerTest() {
        Random random = new Random();
        String userName = UUIDUtils.generateUUID().substring(0, 5);
        String inputPass = UUIDUtils.generateUUID().substring(0, 8);
        String mobile = "" + 1;
        for (int i = 0; i < 10; i++) {
            mobile += random.nextInt(10);
        }
        String finalPass = MD5Utils.inputPass2dbPass(inputPass);
        UserEntity userEntity = new UserEntity();
        userEntity.setMobile(mobile);
        userEntity.setNickname(userName);
        userEntity.setPassword(finalPass);
        userEntity.setSalt("1a2b3c4d");

        UserEntity registered = userService.register(userEntity);

        Integer id = registered.getId();
    }
}
