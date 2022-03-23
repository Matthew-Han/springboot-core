package xyz.yuanmo.springboot.core.exception;

import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.StringJoiner;

/**
 * @author <a href="https://github.com/Matthew-Han">Matthew Han</a>
 * @date 2022/2/10 00:36 10
 * @since 1.0
 **/
@RestControllerAdvice
public class DefaultExceptionHandler {

    /**
     * Exception
     * {@link Order} 设置扫描优先级为最低
     *
     * @param e
     * @return
     */
    @Order
    @ExceptionHandler(Exception.class)
    public R<?> handleException(Exception e) {
        e.printStackTrace();
        return R.fail("[error msg] : " + new StringJoiner("|", "[", "]").add(String.valueOf(e)));
    }


    /**
     * 通用
     * DefaultException
     *
     * @param e
     * @return
     */
    @Order(Integer.MIN_VALUE)
    @ExceptionHandler(DefaultException.class)
    public R<?> handleDefaultException(DefaultException e) {
        e.printStackTrace();
        return R.fail(-1, e.getMessage());
    }

}
