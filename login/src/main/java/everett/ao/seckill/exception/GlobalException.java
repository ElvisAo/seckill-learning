package everett.ao.seckill.exception;

import everett.ao.seckill.utils.CodeMsg;
import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private CodeMsg cm;

    public GlobalException(CodeMsg cm) {
        super(cm.toString());
        this.cm = cm;
    }
}
