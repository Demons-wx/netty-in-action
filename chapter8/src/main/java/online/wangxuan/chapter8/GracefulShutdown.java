package online.wangxuan.chapter8;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;

import java.net.InetSocketAddress;

/**
 * @author wangxuan
 * @date 2018/4/30 下午6:56
 */

public class GracefulShutdown {

    public static void main(String[] args) {
        GracefulShutdown client = new GracefulShutdown();
        client.bootstrap();
    }

    private void bootstrap() {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                        System.out.println("Received data");
                    }
                });

        bootstrap.connect(new InetSocketAddress("www.manning.com", 80)).syncUninterruptibly();

        // shutdownGracefully()将释放所有资源，并且关闭所有当前正在使用中的Channel
        Future<?> future = group.shutdownGracefully();
        // block until the group has shutdown
        future.syncUninterruptibly();
    }
}
