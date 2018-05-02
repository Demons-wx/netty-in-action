package online.wangxuan.chapter8;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author wangxuan
 * @date 2018/4/30 下午4:34
 */

public class BootstrapSharingEventLoopGroup {

    public void bootstrap() {
        // 创建ServerBootstrap以创建ServerSocketChannel，并绑定它
        ServerBootstrap bootstrap = new ServerBootstrap();
        // 设置EventLoopGroup，其将提供用于处理Channel事件的EventLoop
        bootstrap.group(new NioEventLoopGroup(), new NioEventLoopGroup())
                // 指定要使用的Channel使用
                .channel(NioServerSocketChannel.class)
                // 设置用于处理已被接受的子Channel的I/O和数据的ChannelInboundHandler
                .childHandler(new SimpleChannelInboundHandler<ByteBuf>() {
                    ChannelFuture connectFuture;

                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                        // 创建一个Bootstrap类的实例以连接到远程主机
                        Bootstrap bootstrap = new Bootstrap();
                        bootstrap.channel(NioSocketChannel.class).handler(
                                // 为入站I/O设置ChannelInboundHandler
                                new SimpleChannelInboundHandler<ByteBuf>() {
                                    @Override
                                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                                        System.out.println("Received data");
                                    }
                        });
                        // 使用与分配给已被接受的子Channel相同的EventLoop
                        bootstrap.group(ctx.channel().eventLoop());
                        // 连接到远程节点
                        connectFuture = bootstrap.connect(
                                new InetSocketAddress("www.manning.com", 80));
                    }

                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                        // 当连接完成时，执行一些数据操作(如代理)
                        if (connectFuture.isDone()) {
                            // do something with the data
                        }
                    }
                });

        // 通过配置好的ServerBootstrap绑定该ServerSocketChannel
        ChannelFuture future = bootstrap.bind(new InetSocketAddress(8080));
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("Server bound");
                } else {
                    System.err.println("Bind attempt failed");
                    future.cause().printStackTrace();
                }
            }
        });
    }
}
