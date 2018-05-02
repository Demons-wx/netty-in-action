package online.wangxuan.chapter8;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author wangxuan
 * @date 2018/4/30 上午11:39
 */

public class BootstrapClient {

    public static void main(String[] args) {
        BootstrapClient client = new BootstrapClient();
        client.bootstrap();
    }

    private void bootstrap() {

        EventLoopGroup group = new NioEventLoopGroup();
        // 创建一个Bootstrap类的实例以创建和连接新的客户端实例
        Bootstrap bootstrap = new Bootstrap();
        // 设置EventLoopGroup,提供用于处理Channel事件的EventLoop
        bootstrap.group(group)
                // 指定要使用的Channel实现
                .channel(NioSocketChannel.class)
                // 设置用于channel事件和数据的ChannelInboundHandler
                .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                        System.out.println("Received data");
                    }
                });
        // 连接到远程主机
        ChannelFuture future = bootstrap.connect(new InetSocketAddress("www.manning.com", 80));
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("Connection established");
                } else {
                    System.err.println("Connection attempt failed");
                    future.cause().printStackTrace();
                }
            }
        });

    }
}
