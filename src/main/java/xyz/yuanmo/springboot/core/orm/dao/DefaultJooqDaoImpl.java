package xyz.yuanmo.springboot.core.orm.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jooq.*;
import org.jooq.conf.ParamType;
import org.jooq.impl.DAOImpl;
import org.jooq.impl.DSL;
import xyz.yuanmo.springboot.core.orm.model.PageRes;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import static org.jooq.impl.DSL.row;

/**
 * @param <R> record
 * @param <P> POJO
 * @param <T> The generic primary key type. {@link Record}
 * @author <a href="https://github.com/Matthew-Han">Matthew Han</a>
 * @date 2022/2/14 16:03 14
 * @since 1.0
 **/
@Slf4j
public abstract class DefaultJooqDaoImpl<R extends UpdatableRecord<R>, P, T> extends DAOImpl<R, P, T> implements BaseJooqBizDao<R, P, T> {


    @Resource
    public ObjectMapper mapper;

    protected DefaultJooqDaoImpl(Table<R> table, Class<P> type) {
        super(table, type);
    }

    protected DefaultJooqDaoImpl(Table<R> table, Class<P> type, Configuration configuration) {
        super(table, type, configuration);
    }


    /**
     * 从 POJO 中提取 ID
     *
     * @param object
     */
    @Override
    public abstract T getId(P object);


    /**
     * 获取 DSLContext
     *
     * @return DSLContext
     */
    @Override
    public DSLContext create() {
        return DSL.using(configuration());
    }

    /**
     * 开启分页, 并执行 jooq 分页相关方法
     *
     * @param page
     * @param selectLimitStep
     * @return
     */
    public SelectLimitPercentAfterOffsetStep<?> fetchPage(PageRes page, SelectLimitStep<?> selectLimitStep) {
        long limit = Math.max(0, page.getLimit());
        long offset = Math.max(0, page.getOffset() - 1) * limit;
        long total = create().fetchCount(selectLimitStep);
        SelectLimitPercentAfterOffsetStep<?> step = selectLimitStep.offset(offset).limit(limit);
        page.helper(limit, total);
        return step;
    }

    /**
     * 由于 jooq 生态不是太行, 没有类似 pageHelper 这样的工具, 只能自己简单写一个
     *
     * @param page            page model
     * @param selectLimitStep dsl
     * @param type            fetch into 的实体类型
     * @param <O>fetch        into 的实体
     * @return
     */
    @Override
    public <O> List<O> fetchPage(PageRes page, SelectLimitStep<?> selectLimitStep, Class<O> type) {
        SelectLimitPercentAfterOffsetStep<?> step = fetchPage(page, selectLimitStep);
        log.info(step.getSQL(ParamType.INLINED));
        return step.fetchInto(type);
    }


    /**
     * @param page
     * @param selectLimitStep
     * @param mapperFunction
     * @param <RES>           这里的泛型命名不能用 R, 因为 是 SelectLimitStep<? extends R>, 使用 R 作为泛型会被强制变成 Record的 子类!! 有一点点坑
     * @return
     * @see SelectLimitStep
     */
    @Override
    public <RES> RES fetchPageMap(PageRes page, SelectLimitStep<?> selectLimitStep, MapperFunction<SelectLimitPercentAfterOffsetStep<?>, RES> mapperFunction) {
        SelectLimitPercentAfterOffsetStep<?> step = fetchPage(page, selectLimitStep);
        return mapperFunction.mapping(step);
    }

    // ==================================================== update ====================================================




}

