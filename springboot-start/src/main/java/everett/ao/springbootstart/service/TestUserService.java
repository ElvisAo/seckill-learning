package everett.ao.springbootstart.service;

import com.baomidou.mybatisplus.extension.service.IService;
import everett.ao.springbootstart.entity.TestUserEntity;

public interface TestUserService extends IService<TestUserEntity> {

    void deleteByidTx(int id);
}
