package online.wangxuan.chapter1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;


/**
 * @author wangxuan
 * @date 2018/4/7
 */
public class ConnectExample {

    private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();

    public static void connect() {
        Channel channel = CHANNEL_FROM_SOMEWHERE;
        ChannelFuture future = channel.connect(new InetSocketAddress("127.0.0.1", 10001));
        future.addListener(channelFuture -> {
            if (channelFuture.isSuccess()) {
                ByteBuf buffer = Unpooled.copiedBuffer("Hello", Charset.defaultCharset());
                ChannelFuture wf = future.channel().writeAndFlush(buffer);
            } else {
                Throwable cause = future.cause();
                cause.printStackTrace();
            }
        });
    }
}
