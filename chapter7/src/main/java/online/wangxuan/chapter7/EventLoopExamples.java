package online.wangxuan.chapter7;

import io.netty.bootstrap.AbstractBootstrap;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Listing 7.1 Executing tasks in an event loop
 * @author wangxuan
 * @date 2018/4/24 下午11:44
 */

public class EventLoopExamples {

    public static void executeTaskInEventLoop() {
        boolean terminated = true;
        //...
        while (!terminated) {
            List<Runnable> readyEvents = blockUtilEventsReady();
            for (Runnable event : readyEvents) {
                event.run();
            }
        }
    }

    private static final List<Runnable> blockUtilEventsReady() {
        return Collections.<Runnable>singletonList(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
