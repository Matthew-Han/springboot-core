package xyz.yuanmo.springboot.core.orm.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import xyz.yuanmo.springboot.core.BaseIterable;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * @author <a href="https://github.com/Matthew-Han">Matthew Han</a>
 * @date 2022/2/9 21:16 09
 * @since 1.0
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BaseEntity extends BaseIterable implements Serializable {


    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer status;

    private LocalDateTime created;

    private LocalDateTime updated;


}
