package online.wangxuan.chapter6;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Using SimpleChannelInboundHandler
 * @author wangxuan
 * @date 2018/4/21 下午10:05
 */

public class SimpleDiscardHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        // 不需要任何显式地资源释放
    }
}
