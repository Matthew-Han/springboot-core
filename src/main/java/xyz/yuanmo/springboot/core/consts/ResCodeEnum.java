package xyz.yuanmo.springboot.core.consts;

/**
 * @author <a href="https://github.com/Matthew-Han">Matthew Han</a>
 * @date 2022/2/9 23:33 09
 * @since 1.0
 **/
public enum ResCodeEnum implements AbstractStatusEnum {
    /**
     * 成功
     */
    SUCCESS(200, "OK"),

    /**
     * 请求类型错误
     */
    REQUEST_ERROR(-1, "不正确的请求类型:"),


    /**
     * 参数错误
     */
    PARAMS_ERROR(-1, "参数错误"),

    /**
     * 参数错误
     */
    ILLEGAL_REQUEST(-1, "不合法的请求"),

    /**
     * 未知的错误
     */
    UNKNOWN_ERROR(-1, "未知错误"),


    ;

    private final Integer code;
    private final String message;

    ResCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * get code
     *
     * @return code
     */
    @Override
    public Integer getCode() {
        return this.code;
    }

    /**
     * get message
     *
     * @return message
     */
    @Override
    public String getMessage() {
        return this.message;
    }
}
