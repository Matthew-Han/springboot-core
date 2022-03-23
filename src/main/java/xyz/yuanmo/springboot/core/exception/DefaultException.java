package xyz.yuanmo.springboot.core.exception;


import xyz.yuanmo.springboot.core.consts.AbstractStatusEnum;

/**
 * @author <a href="https://github.com/Matthew-Han">Matthew Han</a>
 * @date 2022/2/9 23:37 09
 * @since 1.0
 **/
public class DefaultException extends RuntimeException {

    /**
     * 错误编码
     */
    private Object errorCode;


    enum Type {
        /**
         * 业务异常
         */
        BIZ,
        /**
         * 未知异常
         */
        UNKNOWN,
        /**
         * OSS
         */
        OSS,
        ;
    }

    public Object getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Object errorCode) {
        this.errorCode = errorCode;
    }


    public DefaultException(Throwable cause) {
        super(cause);
    }

    public DefaultException(String message) {
        super(message);
    }

    public DefaultException(String message, Throwable cause) {
        super(message, cause);
    }

    public DefaultException(AbstractStatusEnum pair) {
        super(pair.getMessage());
        this.setErrorCode(pair.getCode());
    }

    public DefaultException(AbstractStatusEnum pair, Throwable cause) {
        super(pair.getMessage(), cause);
        this.setErrorCode(pair.getCode());
    }


}
