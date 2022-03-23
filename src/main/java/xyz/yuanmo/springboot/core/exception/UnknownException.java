package xyz.yuanmo.springboot.core.exception;


import com.zrtg.hydra.minchir.core.consts.AbstractStatusEnum;

/**
 * @author <a href="https://github.com/Matthew-Han">Matthew Han</a>
 * @date 2022/2/10 00:16 10
 * @since 1.0
 **/
public class UnknownException extends DefaultException {

    public UnknownException(Throwable cause) {
        super(cause);
    }

    public UnknownException(String message) {
        super(message);
    }

    public UnknownException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownException(AbstractStatusEnum pair) {
        super(pair.getMessage());
        this.setErrorCode(pair.getCode());
    }

    public UnknownException(AbstractStatusEnum pair, Throwable cause) {
        super(pair.getMessage(), cause);
        this.setErrorCode(pair.getCode());
    }
}
