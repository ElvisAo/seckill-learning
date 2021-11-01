package everett.ao.seckill.vo;

import everett.ao.seckill.validate.MobileValidate;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class LoginVo {
    @MobileValidate
    private String mobile;
    @Length(min = 32)
    private String password;
}
