package xyz.yuanmo.springboot.core.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.jooq.JSON;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;
import org.springframework.util.ObjectUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href="https://github.com/Matthew-Han">Matthew Han</a>
 * @date 2022/1/19 17:25 19
 * @since 1.0
 **/
public class BeanCopyUtil extends BeanCopier {


    public static <T1, T2> BeanCopier create(Class<T1> t1, Class<T2> t2) {
        return BeanCopier.create(t1, t2, false);
    }

    /**
     * {@link BeanCopier#copy(Object, Object, Converter)} 不是静态方法
     * 重写一个
     * <p>
     * 🎵
     * 在我的冰河时期
     * 与我联系不合时宜
     * 现在我只适合我自己
     * 🎵
     *
     * @param src  来源
     * @param dest 目的
     * @param <T1> 来源
     * @param <T2> 目的
     */
    public static <T1, T2> void copy(T1 src, T2 dest) {
        if (ObjectUtils.isEmpty(src) || ObjectUtils.isEmpty(dest)) {
            return;
        }
        create(src.getClass(), dest.getClass()).copy(src, dest, null);
    }


    /**
     * 针对 自定义的 copy
     * <p>
     * e.g. {@link JSON}
     *
     * @param src
     * @param dest
     * @param converter
     * @param <T1>
     * @param <T2>
     */
    public static <T1, T2> void copyWithConverter(T1 src, T2 dest, Converter converter) {
        if (ObjectUtils.isEmpty(src) || ObjectUtils.isEmpty(dest)) {
            return;
        }
        create(src.getClass(), dest.getClass(), true).copy(src, dest, converter);
    }

    /**
     * 针对以下需求
     * List<?> 的对象当且仅当存在当前一个元素时将其拷贝
     *
     * @param src
     * @param dest
     * @param <T1>
     * @param <T2>
     */
    public static <T1, T2> void listCopyFromObj(List<T1> src, T2 dest) {
        if (ObjectUtils.isEmpty(dest) || ObjectUtils.isEmpty(src)) {
            return;
        }
        if (src.size() == 1) {
            create(src.get(0).getClass(), dest.getClass()).copy(src.get(0), dest, null);
        }
    }

    @SneakyThrows
    public static <T1, T2> List<T2> listCopy(List<T1> src, Class<T2> type) {
        List<T2> ans = new ArrayList<>(src.size());
        if (ObjectUtils.isEmpty(src)) {
            return Collections.emptyList();
        }
        for (T1 t1 : src) {
            T2 o = type.newInstance();
            create(t1.getClass(), type, false).copy(t1, o, null);
            ans.add(o);
        }
        return ans;
    }

    @SneakyThrows
    public static <T1, T2> List<T2> listCopy(List<T1> src, Class<T2> type, Converter converter) {
        List<T2> ans = new ArrayList<>(src.size());
        if (ObjectUtils.isEmpty(src)) {
            return Collections.emptyList();
        }
        for (T1 t1 : src) {
            T2 o = type.newInstance();
            create(t1.getClass(), type, true).copy(t1, o, converter);
            ans.add(o);
        }
        return ans;
    }

    public static <T1, T2> List<T2> jooqJsonCopyHelper(List<T1> src, Class<T1> type1, Class<T2> type2) {
        List<T2> ans = new ArrayList<>(src.size());
        if (ObjectUtils.isEmpty(src)) {
            return Collections.emptyList();
        }
        for (T1 t1 : src) {
            JSONConverterProvider provider = new JSONConverterProvider();
            org.jooq.Converter<T1, T2> provide = provider.provide(type1, type2);
            ans.add(provide.from(t1));
        }
        return ans;
    }

    /**
     * @param o1
     * @param o2
     * @param converter
     */
    @Override
    public void copy(Object o1, Object o2, Converter converter) {
        if (ObjectUtils.isEmpty(o1) || ObjectUtils.isEmpty(o2)) {
            return;
        }
        create(o1.getClass(), o2.getClass()).copy(o1, o2, converter);
    }

    /**
     * 针对 List<T> 这种泛型反序列化
     * Java 真的太逊了, 1.8 反正还是伪泛型
     *
     * @param collectionClass
     * @param elementClasses
     * @return
     */
    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return new ObjectMapper().getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }


    /**
     * 拒绝实体每个属性判断 null 了, 烦麻了!
     *
     * @param src
     * @param collectionClass
     * @param elementClasses
     * @param <T>
     * @return
     */
    public static <T> T multiCopy(String src, Class<?> collectionClass, Class<?>... elementClasses) {
        if (ObjectUtils.isEmpty(src)) {
            return null;
        }
        try {
            JavaType javaType = getCollectionType(collectionClass, elementClasses);
            return new ObjectMapper().readValue(src, javaType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T multiCopy(String src, Class<T> destClass) {
        if (ObjectUtils.isEmpty(src)) {
            return null;
        }
        try {
            return new ObjectMapper().readValue(src, destClass);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


}