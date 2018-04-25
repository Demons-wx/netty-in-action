package online.wangxuan.chapter6;


import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import static io.netty.channel.DummyChannelPipeline.DUMMY_INSTANCE;
/**
 * Modify the ChannelPipeline
 * @author wangxuan
 * @date 2018/4/21 下午11:59
 */

public class ModifyChannelPipeline {

    private static final ChannelPipeline CHANNEL_PIPELINE_FROM_SOMEWHERE = DUMMY_INSTANCE;

    public static void modifyPipeline() {
        ChannelPipeline pipeline = CHANNEL_PIPELINE_FROM_SOMEWHERE;
        FirstHandler firstHandler = new FirstHandler();
        pipeline.addLast("handler1", firstHandler);
        pipeline.addFirst("handler2", new SecondHandler());
        pipeline.addLast("handler3", new ThirdHandler());
        //...
        // 通过名称移除handler3
        pipeline.remove("handler3");
        // 通过引用移除FirstHandler
        pipeline.remove(firstHandler);
        // 将SecondHandler换成FourthHandler
        pipeline.replace("handler2", "handler4", new FourthHandler());
    }

    private static final class FirstHandler extends ChannelHandlerAdapter {}
    private static final class SecondHandler extends ChannelHandlerAdapter {}
    private static final class ThirdHandler extends ChannelHandlerAdapter {}
    private static final class FourthHandler extends ChannelHandlerAdapter {}
}
