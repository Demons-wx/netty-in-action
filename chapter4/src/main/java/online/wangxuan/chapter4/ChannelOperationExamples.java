package online.wangxuan.chapter4;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import online.wangxuan.chapter4.util.MyThreadPool;

import java.util.concurrent.ExecutorService;

/**
 * @author wangxuan
 * @date 2018/4/16 上午12:42
 */

public class ChannelOperationExamples {

    private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();

    public static void writingToChannel() {
        Channel channel = CHANNEL_FROM_SOMEWHERE;
        // 创建持有要写数据的ByteBuf
        ByteBuf buf = Unpooled.copiedBuffer("your data", CharsetUtil.UTF_8);
        // 写数据并冲刷它
        ChannelFuture cf = channel.writeAndFlush(buf);
        // 创建ChannelFutureListener以便在写操作完成后接收通知
        cf.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("Write successful");
                } else {
                    System.err.println("Write error");
                    future.cause().printStackTrace();
                }
            }
        });
    }

    public static void writingToChannelFromManyThreads() {
        final Channel channel = CHANNEL_FROM_SOMEWHERE;
        final ByteBuf buf = Unpooled.copiedBuffer("your data", CharsetUtil.UTF_8);
        Runnable write = () -> {
            channel.write(buf.duplicate());
        };

        ExecutorService exec = new MyThreadPool().getExecutor("writing concurrent");
        // write in one thread
        exec.execute(write);
        // write in another thread
        exec.execute(write);
    }
}
