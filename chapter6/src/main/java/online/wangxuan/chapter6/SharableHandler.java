package online.wangxuan.chapter6;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * A sharable ChannelHandler
 * @author wangxuan
 * @date 2018/4/22 下午2:25
 */
@Sharable
public class SharableHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channel read message " + msg);
        // 记录方法调用，并转发给下一个ChannelHandler
        ctx.fireChannelRead(msg);
    }
}
