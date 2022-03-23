package xyz.yuanmo.springboot.core;

import lombok.SneakyThrows;
import xyz.yuanmo.springboot.core.exception.DefaultException;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author <a href="https://github.com/Matthew-Han">Matthew Han</a>
 * @date 2022/2/10 01:09 10
 * @since 1.0
 **/
public abstract class BaseIterable implements Iterable<Object> {


    private Object object;

    private Integer layer;

    public BaseIterable() {
    }

    /**
     * 开启迭代器
     *
     * @param object
     */
    public void enableIterator(Object object) {
        this.object = object;
    }

    /**
     * 设置最大递归数(找父类)
     *
     * @param layer
     */
    public void setLayer(int layer) {
        this.layer = layer;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Object> iterator() {
        if (object != null) {
            return new BaseIterator(object, layer == null ? 0x3f3f3f3f : layer);
        } else {
            throw new DefaultException("你在干勾⑧呢?! 必须传内个进去");
        }
    }


    public static class BaseIterator implements Iterator<Object> {

        private int cursor;
        private final int size;
        private final Object o;
        private final List<Field> fields;


        public BaseIterator(Object o) {
            this.o = o;
            this.fields = dfs(o.getClass());
            this.cursor = 0;
            this.size = fields.size();
        }

        /**
         * 可以不到 root, 指定深度, 但是实际情况是 Math.min(layer, factDepth);
         *
         * @param o
         * @param layer 选择层数
         */
        public BaseIterator(Object o, int layer) {
            this.o = o;
            this.fields = dfs(o.getClass(), layer);
            this.cursor = 0;
            this.size = fields.size();
        }

        /**
         * 找到父类为 {@link BaseIterable} 为止
         *
         * @param o
         * @return
         */
        private List<Field> dfs(Class<?> o) {
            List<Field> curr = new ArrayList<>();
            if (o.getSuperclass() != BaseIterable.class) {
                curr.addAll(dfs(o.getSuperclass()));
            }
            curr.addAll(Arrays.asList(o.getDeclaredFields()));
            return curr;
        }

        /**
         * Math.min(layer, fact);
         * 找到父类为 {@link BaseIterable} 为止 或者 layer 为 1(当前层)
         *
         * @param o
         * @param layer
         * @return
         */
        private List<Field> dfs(Class<?> o, int layer) {
            List<Field> curr = new ArrayList<>();
            if (o.getSuperclass() != BaseIterable.class && layer > 1) {
                curr.addAll(dfs(o.getSuperclass(), layer - 1));
            }
            curr.addAll(Arrays.asList(o.getDeclaredFields()));
            return curr;
        }

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @SneakyThrows
        @Override
        public Object next() {
            Field field = fields.get(cursor++);
            field.setAccessible(true);
            return field.get(o);
        }
    }
}