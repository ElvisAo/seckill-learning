package everett.ao.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import everett.ao.seckill.entity.TestUserEntity;

public interface TestUserService extends IService<TestUserEntity> {

    void deleteByidTx(int id);
}
