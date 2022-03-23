package xyz.yuanmo.springboot.core.exception;


import xyz.yuanmo.springboot.core.consts.AbstractStatusEnum;

/**
 * @author <a href="https://github.com/Matthew-Han">Matthew Han</a>
 * @date 2022/2/10 00:16 10
 * @since 1.0
 **/
public class BizException extends DefaultException {

    public BizException(Throwable cause) {
        super(cause);
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(AbstractStatusEnum pair) {
        super(pair.getMessage());
        this.setErrorCode(pair.getCode());
    }

    public BizException(AbstractStatusEnum pair, Throwable cause) {
        super(pair.getMessage(), cause);
        this.setErrorCode(pair.getCode());
    }


}
