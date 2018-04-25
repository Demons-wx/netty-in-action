package online.wangxuan.chapter4.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author wangxuan
 * @date 2018/4/7
 */
public class MyThreadPool {

    public ExecutorService getExecutor(String name) {

        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat(name + "-pool-%d").build();

        return new ThreadPoolExecutor(5, 10,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

    }


}
