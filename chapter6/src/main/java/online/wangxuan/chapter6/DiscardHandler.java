package online.wangxuan.chapter6;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * Releasing message resources
 * @author wangxuan
 * @date 2018/4/21 下午9:59
 */

public class DiscardHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 丢弃已接收的消息
        ReferenceCountUtil.release(msg);
    }
}
