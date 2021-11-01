package everett.ao.springbootstart.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import everett.ao.springbootstart.entity.TestUserEntity;
import everett.ao.springbootstart.mapper.TestUserMapper;
import everett.ao.springbootstart.service.TestUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestUserServiceImpl extends ServiceImpl<TestUserMapper, TestUserEntity> implements TestUserService {
    @Transactional
    public void deleteByidTx(int id) {
        baseMapper.deleteById(id);
        int r = 10 / 0;
    }
}
