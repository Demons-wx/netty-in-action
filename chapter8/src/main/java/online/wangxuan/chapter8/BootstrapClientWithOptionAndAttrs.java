package online.wangxuan.chapter8;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.net.InetSocketAddress;

/**
 * @author wangxuan
 * @date 2018/4/30 下午6:10
 */

public class BootstrapClientWithOptionAndAttrs {

    public void bootstrap() {
        // 创建一个AttributeKey以标识该属性
        final AttributeKey<Integer> id = AttributeKey.newInstance("ID");
        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                    @Override
                    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
                        // 使用AttributeKey检索属性以及它的值
                        Integer inValue = ctx.channel().attr(id).get();
                        // do something with the idValue
                    }

                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                        System.out.println("Received data");
                    }
                });

        // 设置ChannelOption，其将在connect()或者bind()方法被调用时被设置到已经创建的Channel上
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);
        // 存储该id属性
        bootstrap.attr(id, 123456);
        // 使用配置好的Bootstrap实例连接到远程主机
        ChannelFuture future = bootstrap.connect(new InetSocketAddress("www.manning.com", 80));
        future.syncUninterruptibly();
    }
}
