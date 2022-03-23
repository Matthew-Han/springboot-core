package xyz.yuanmo.springboot.core.util;

import org.springframework.util.ObjectUtils;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author <a href="https://github.com/Matthew-Han">Matthew Han</a>
 * @date 2022/3/8 16:07 08
 * @since 1.0
 **/
public class SimpleDateUtil {



    public static LocalDateTime timestamp2LocalDateTime(Long t) {
        return timestamp2LocalDateTime(t, ZoneOffset.ofHours(8));
    }

    public static LocalDateTime timestamp2LocalDateTime(Long t, ZoneOffset offset) {
        if (!ObjectUtils.isEmpty(t)) {
            return Instant.ofEpochSecond(t).atOffset(offset).toLocalDateTime();
        } else {
            return null;
        }
    }


    public static LocalDateTime milliTimestamp2LocalDateTime(Long t) {
        return milliTimestamp2LocalDateTime(t, ZoneOffset.ofHours(8));
    }

    public static LocalDateTime milliTimestamp2LocalDateTime(Long t, ZoneOffset offset) {
        if (!ObjectUtils.isEmpty(t)) {
            return Instant.ofEpochMilli(t).atOffset(offset).toLocalDateTime();
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
        Long ss = null;
        System.out.println(timestamp2LocalDateTime(ss));
    }


}
