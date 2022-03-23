package xyz.yuanmo.springboot.core.orm.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author <a href="https://github.com/Matthew-Han">Matthew Han</a>
 * @date 2022/2/9 21:31 09
 * @since 1.0
 **/
@Data
@AllArgsConstructor
public class BaseQuery {

    private Integer id;
    private Integer status;
    private Long startTime;
    private Long endTime;
    private String ver;
    private String author;

    private PageRes page;



}
