package xyz.yuanmo.springboot.core;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.*;

/**
 * @author <a href="https://github.com/Matthew-Han">Matthew Han</a>
 * @date 2022/2/24 12:36 24
 * @since 1.0
 **/
@Slf4j
public class ThreadPoolFactory {

    private static final long KEEP_ALIVE_TIME = 10L;


    private static final int BLOCKING_QUEUE_MAX = 2 << 12;


    /**
     * 自用 MacBook 4 核
     */
    private static final int SIZE_CORE_POOL = 2 << 1;

    /**
     * 线程池维护线程的最大数量
     * size / (1 - 0.9)
     */
    private static final int SIZE_MAX_POOL = 2 << 2;

    /**
     * 禁止手动初始化
     */
    private ThreadPoolFactory() {
    }


    public static void printPoolInfo() {
        log.info("当前线程Pool的数量 = [{}]", Singleton.SINGLETON.getDefaultThreadPool().getPoolSize());
        log.info("当前task的数量 = [{}]", Singleton.SINGLETON.getDefaultThreadPool().getTaskCount());
        log.info("当前执行task的数量 = [{}]", Singleton.SINGLETON.getDefaultThreadPool().getActiveCount());
        log.info("当前完成task的数量 = [{}]", Singleton.SINGLETON.getDefaultThreadPool().getCompletedTaskCount());
    }


    /**
     * 通过枚举创建单例对象
     */
    private enum Singleton {
        /**
         * 线程池单例
         */
        SINGLETON;
        private final ThreadPoolExecutor defaultThreadPool;
        private final ScheduledExecutorService service;

        Singleton() {
            // 为线程命名
            ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNamePrefix("salver-worker-").build();
            // 创建线程池 1
            defaultThreadPool = new ThreadPoolExecutor(
                    SIZE_CORE_POOL,
                    SIZE_MAX_POOL,
                    KEEP_ALIVE_TIME,
                    TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<>(BLOCKING_QUEUE_MAX),
                    namedThreadFactory);
            // 创建线程池 2 - 周期性任务
            service = Executors.newScheduledThreadPool(4);
        }

        /**
         * 返回单例对象
         */
        public ThreadPoolExecutor getDefaultThreadPool() {
            return defaultThreadPool;
        }

        public ScheduledExecutorService getScheduledThreadPool() {
            return service;
        }

    }

    public static ThreadPoolExecutor getDefaultThreadPool() {
        return Singleton.SINGLETON.defaultThreadPool;
    }

    /**
     * 向池中添加任务
     * 单次执行
     *
     * @param task
     */
    @SneakyThrows
    public static void addExecuteTask(Runnable task) {
        if (task != null) {
            ThreadPoolExecutor threadPoolExecutor = Singleton.SINGLETON.getDefaultThreadPool();
            threadPoolExecutor.execute(task);
            // 不要设置成阻塞的串行化
            // future.get(1000 * 60, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * 带返回值的 callable
     *
     * @param task
     * @param <T>
     * @return
     * @see Callable
     */
    @SneakyThrows
    public static <T> Future<T> addExecuteTask(Callable<T> task) {
        Future<T> res = null;
        if (task != null) {
            ThreadPoolExecutor threadPoolExecutor = Singleton.SINGLETON.getDefaultThreadPool();
            res = threadPoolExecutor.submit(task);
            // 不要设置成阻塞的串行化
            // future.get(1000 * 60, TimeUnit.MILLISECONDS);
        }
        return res;
    }

    public static void addScheduleTask(Runnable task) {
        Singleton.SINGLETON.getScheduledThreadPool().scheduleWithFixedDelay(task, 5, 3, TimeUnit.SECONDS);
    }

    public static <T> ScheduledFuture<T> addScheduleTask(Callable<T> task) {
        return Singleton.SINGLETON.getScheduledThreadPool().schedule(task, 30, TimeUnit.DAYS);
    }

}
