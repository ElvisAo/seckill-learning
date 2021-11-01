package everett.ao.seckill.validate;

import com.mysql.cj.util.StringUtils;
import everett.ao.seckill.utils.ValidatorUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MobileValidator implements ConstraintValidator<MobileValidate, String> {
    private Boolean required = false;

    @Override
    public void initialize(MobileValidate constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (required) {
            return ValidatorUtil.mobileValidate(value);
        } else {
            if (StringUtils.isNullOrEmpty(value)) return true;
            else return ValidatorUtil.mobileValidate(value);
        }
    }
}
