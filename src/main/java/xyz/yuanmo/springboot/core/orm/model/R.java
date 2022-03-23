package xyz.yuanmo.springboot.core.orm.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author <a href="https://github.com/Matthew-Han">Matthew Han</a>
 * @date 2022/2/9 21:30 09
 * @since 1.0
 **/
@Data
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Object code;

    private Boolean success;

    private String message;

    private T data;

    private LocalDateTime time;

    private PageRes page;

    public R() {
        this.code = 200;
        this.success = true;
        this.message = "success";
        this.time = LocalDateTime.now();
    }

    public R(T data) {
        this.code = 200;
        this.success = true;
        this.message = "success";
        this.data = data;
        this.time = LocalDateTime.now();
    }

    public R(T data, String msg) {
        this(data);
        this.message = msg;
    }

    public R(T data, PageRes page) {
        this(data);
        this.page = page;
    }

    public R(T data, String msg, PageRes page) {
        this(data);
        this.message = msg;
        this.page = page;
    }

    private R(Object code, boolean success, T data, String msg) {
        this(data);
        this.code = code;
        this.success = success;
        this.message = msg;
    }

    /* ============================================================================================================== */

    public static <T> R<T> success() {
        return new R<>();
    }

    public static <T> R<T> success(T data) {
        return new R<>(data);
    }

    public static <T> R<T> success(T data, String msg) {
        return new R<>(data, msg);
    }

    public static <T> R<T> success(T data, PageRes page) {
        return new R<>(data, page);
    }

    public static <T> R<T> success(T data, String msg, PageRes page) {
        return new R<>(data, msg, page);
    }

    public static <T> R<T> fail(String msg) {
        return new R<>(-1, false, null, msg);
    }

    public static <T> R<T> fail(Object code, String msg) {
        return new R<>(code, false, null, msg);
    }

    public static void main(String[] args) {
        System.out.println(R.fail("fail"));
        System.out.println(R.success(new Object()));
        System.out.println(R.success(new Object(), "OK"));
        System.out.println(R.success(new Object(), "OK", new PageRes(3, 10)));
    }


}
