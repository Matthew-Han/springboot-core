package xyz.yuanmo.springboot.core.orm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.yuanmo.springboot.core.exception.DefaultException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;

/**
 * @author <a href="https://github.com/Matthew-Han">Matthew Han</a>
 * @date 2022/2/14 15:41 14
 * @since 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageRes implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前页
     */
    private long offset = 1;

    /**
     * 每页的数量
     */
    private long limit = 10;

    /**
     * 总页数
     */
    private long pages;

    /**
     * 条数
     */
    private long total;


    public PageRes(long offset, long limit) {
        this.offset = offset;
        this.limit = limit;
    }

    public void helper(long limit, long total) {
        if (limit < 0) {
            throw new DefaultException("分页设置不能有负数");
        }
        this.pages = limit != 0 ? total / limit + (total % limit == 0 ? 0 : 1) : 0;
        this.total = total;
    }

    public static void helper(int[] rowBounds) {
        if (rowBounds[0] == 0 && rowBounds[1] == Integer.MAX_VALUE) {
            System.out.println("rowBounds = " + Arrays.toString(rowBounds));
        } else {
            int size = rowBounds[1] != 0 ? (int) (Math.ceil(((double) rowBounds[0] + rowBounds[1]) / rowBounds[1])) : 0;
            System.out.println(size);
        }
    }

    public static void main(String[] args) {
        PageRes res = new PageRes(2, 1);
        System.out.println(res);
        res = new PageRes(10, Integer.MAX_VALUE);
        System.out.println(res);
        res = new PageRes(Long.MAX_VALUE - 1, Long.MAX_VALUE);
        System.out.println(res);


        BigDecimal decimal = new BigDecimal("3");
        BigDecimal divide = decimal.divide(new BigDecimal("2"), 1);
        System.out.println(divide.longValue());

        helper(new int[]{0, Integer.MAX_VALUE});


    }

}
