package xyz.yuanmo.springboot.core.consts;

/**
 * @author <a href="https://github.com/Matthew-Han">Matthew Han</a>
 * @date 2022/2/9 23:32 09
 * @since 1.0
 **/
public interface AbstractStatusEnum {

    /**
     * get code
     *
     * @return code
     */
    Integer getCode();

    /**
     * get message
     *
     * @return message
     */
    String getMessage();
}
