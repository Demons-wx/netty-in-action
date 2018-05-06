package online.wangxuan.chapter11;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * @author wangxuan
 * @date 2018/5/6 下午5:14
 */

public class HttpPipelineInitializer extends ChannelInitializer<Channel> {

    private final boolean client;

    public HttpPipelineInitializer(boolean client) {
        this.client = client;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 如果是客户端
        if (client) {
            // 则添加HttpResponseDecoder以处理来自服务器的响应
            pipeline.addLast("decoder", new HttpResponseDecoder());
            // 添加HttpRequestEncoder以向服务器发送请求
            pipeline.addLast("encoder", new HttpRequestEncoder());
        // 如果是服务器
        } else {
            // 则添加HttpRequestDecoder以接受来自客户端的请求
            pipeline.addLast("decoder", new HttpRequestDecoder());
            // 添加HttpResponseEncoder以向客户端发送响应
            pipeline.addLast("encoder", new HttpResponseEncoder());
        }
    }
}
