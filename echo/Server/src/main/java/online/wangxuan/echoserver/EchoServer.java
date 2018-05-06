package online.wangxuan.echoserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by wx
 * Date: 2018/4/6
 */
public class EchoServer {

    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: " + EchoServer.class.getSimpleName() + "<port>");
        }

        int port = Integer.parseInt(args[0]);
        new EchoServer(port).start();

    }

    private void start() throws Exception {

        final EchoServerHandler serverHandler = new EchoServerHandler();
        // 创建EventLoopGroup来接受和处理新的连接
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            // 将Channel类型指定为NioServerSocketChannel
            b.group(group).channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    // 此处，当一个新的连接被接收时，一个新的子Channel会被创建，ChannelInitializer将会
                    // 把你的一个EchoServerHandler实例添加到该Channel的ChannelPipeline中
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(serverHandler);
                        }
                    });
            // 绑定服务器，调用sync()方法阻塞，直到绑定完成
            ChannelFuture f = b.bind().sync();
            // 程序将会阻塞等待，直到服务器的Channel关闭
            f.channel().closeFuture().sync();
        } finally {
            // 关闭EventLoopGroup，并释放所有资源
            group.shutdownGracefully().sync();
        }
    }
}
