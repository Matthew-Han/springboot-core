package xyz.yuanmo.springboot.core.orm.dao;

/**
 * @author <a href="https://github.com/Matthew-Han">Matthew Han</a>
 * @date 2022/2/18 12:24 18
 * @since 1.0
 **/
@FunctionalInterface
public interface MapperFunction<O, R> {

    /**
     * 通用映射
     * <p>
     * 比如 select t1.*, t2.*
     * from t1 leftJoin t2 on... where...
     * <p>
     * 直接用 fetchInto, 两表有重名的, 映射就会有点问题, 得写 as, 太勾⑧麻烦了.
     * 但好像写 as 还是比这个简单, wdnmd ???
     *
     * @param o records
     * @return target
     */
    public R mapping(O o);
}
