package everett.ao.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import everett.ao.seckill.entity.UserEntity;
import everett.ao.seckill.vo.LoginVo;

import javax.servlet.http.HttpServletResponse;

public interface UserService extends IService<UserEntity> {
    UserEntity login(LoginVo loginVo);

    UserEntity getByToken(HttpServletResponse response, String token);
}
