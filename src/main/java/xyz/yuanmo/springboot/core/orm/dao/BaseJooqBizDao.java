package xyz.yuanmo.springboot.core.orm.dao;

import org.jooq.*;
import xyz.yuanmo.springboot.core.orm.model.PageRes;
import java.util.List;


/**
 * 一些通用的 DAO 方法 ~
 * bitch!
 *
 * @author <a href="https://github.com/Matthew-Han">Matthew Han</a>
 * @date 2022/2/14 16:08 14
 * @since 1.0
 **/
public interface BaseJooqBizDao<R extends UpdatableRecord<R>, P, T> extends DAO<R, P, T> {


    /**
     * 获取 DSLContext
     *
     * @return DSLContext
     */
    DSLContext create();


    /**
     * 缝合怪
     *
     * @param pageResult
     * @param selectLimitStep
     * @param mapper
     * @param <O>
     * @return
     */
    default <O> PageRes fetchPage(PageRes pageResult, SelectLimitStep<?> selectLimitStep, RecordMapper<? super Record, O> mapper) {
        return new PageRes();
    }

    /**
     * 由于 jooq 生态不是太行, 没有类似 pageHelper 这样的工具, 只能自己简单写一个
     *
     * @param page            page model
     * @param selectLimitStep dsl
     * @param type            fetch into 的实体类型
     * @param <O>             fetch into 的实体
     * @return
     */
    <O> List<O> fetchPage(PageRes page, SelectLimitStep<?> selectLimitStep, Class<O> type);

    /**
     * 返回自定义映射实体
     *
     * @param page
     * @param selectLimitStep
     * @param mapperFunction
     * @param <RES>
     * @return
     */
    <RES> RES fetchPageMap(PageRes page, SelectLimitStep<?> selectLimitStep, MapperFunction<SelectLimitPercentAfterOffsetStep<?>, RES> mapperFunction);
}
