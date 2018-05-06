package online.wangxuan.chapter11;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * @author wangxuan
 * @date 2018/5/6 下午10:57
 */

public class LineBasedHandlerInitializer extends ChannelInitializer<Channel> {

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 该LineBasedFrameDecoder将提取的帧转发给下一个ChannelInboundHandler
        pipeline.addLast(new LineBasedFrameDecoder(64 * 1024));
        // 添加FrameHandler以接收帧
        pipeline.addLast(new FrameHandler());
    }

    public static final class FrameHandler extends SimpleChannelInboundHandler<ByteBuf> {

        // 传入了单个帧的内容
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
            // Do something with the data extracted from the ctx
        }
    }
}
