package everett.ao.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import everett.ao.seckill.entity.UserEntity;
import everett.ao.seckill.vo.LoginVo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserService extends IService<UserEntity> {
    List<UserEntity> queryAllUser();

    UserEntity login(LoginVo loginVo);

    UserEntity getById(Integer id);

    UserEntity updatePassword(Integer userId, String token, String Password);

    UserEntity getByToken(HttpServletResponse response, String token);

    UserEntity register(UserEntity userEntity);
}
